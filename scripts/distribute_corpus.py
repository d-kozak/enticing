import glob
import logging as log
import os
import sys
import threading

from knot.utils import count_size, execute_command, create_remote_dir, measure, execute_via_ssh

# global configuration
# do NOT change outside of the main method
config = {
    "username": os.environ['USER'],
    "collections_per_server": 6,
    "debug_level": log.INFO
}


def handle_args(args):
    if len(args) != 3:
        raise ValueError("format: input_dir server_file output_dir(on the servers)")

    input_dir = args[0]
    if not os.path.isdir(input_dir):
        raise ValueError(f"{input_dir} is not a directory")
    input_files = glob.glob(input_dir + "/*.mg4j")
    with open(args[1]) as file:
        servers = file.read().split("\n")
        return input_files, servers, args[2]


def execute_local_indexing(output_dir):
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)


def split_files(input_files, nodes):
    extra = len(input_files) % len(nodes)
    per_server = len(input_files) // len(nodes)
    batches = {}
    start = 0
    for i, server in enumerate(nodes):
        count = per_server + 1 if i < extra else per_server
        batch = input_files[start:start + count]
        if not batch:
            break
        batches[server] = batch
        start += count
    return batches


def send_files(server, server_batch, output_dir):
    create_remote_dir(server, config["username"], output_dir)
    log.info(f"server {server} : {count_size(server_batch)} MB : {server_batch} ")
    collections = [f"{server}-{x}" for x in range(config["collections_per_server"])]
    collection_batches = split_files(server_batch, collections)

    for collection, collection_batch in collection_batches.items():
        log.info(f"collection {collection} : {count_size(collection_batch)} MB : {collection_batch}")
        collection_output_dir = f'{output_dir}/{collection}'
        create_remote_dir(server, config["username"], collection_output_dir)
        cmd = f"scp {' '.join(collection_batch)} {config['username']}@{server}:{collection_output_dir}/"
        execute_command(cmd)


def distribute_files(server_batches, output_dir):
    threads = []
    try:
        for server, batch in server_batches.items():
            t = threading.Thread(target=send_files, args=(server, batch, output_dir))
            t.start()
            threads.append(t)
    finally:
        for thread in threads:
            thread.join()
        log.info("all threads finished")


def print_final_stats(servers, output_dir):
    log.info('Final situation: ')
    for server in servers:
        cmd = f'ls -l {output_dir}'
        proc = execute_via_ssh(server, config["username"], cmd)
        log.info(server + ":")
        for collection in [line.split()[-1] for line in proc.stdout.split("\n") if server in line]:
            log.info(f'\t{collection}:')
            cmd = f'ls -l {output_dir}/{collection}'
            proc = execute_via_ssh(server, config["username"], cmd)
            files = [line.split()[-1] for line in proc.stdout.split("\n") if ".mg4j" in line]
            log.info(f'\t\t{files}')


def main():
    config["username"] = "xkozak15"
    log.basicConfig(level=config["debug_level"], format='%(asctime)s - %(levelname)s - %(message)s')
    input_files, servers, output_dir = handle_args(sys.argv[1:])
    if not input_files:
        log.error("No mg4j files found")
        exit(1)
    log.info(f"distributing {len(input_files)} files over {len(servers)} servers({servers})")
    server_batches = split_files(input_files, servers)
    measure(lambda: distribute_files(server_batches, output_dir), name="Distributing", print_duration=True)
    print_final_stats(servers, output_dir)


if __name__ == "__main__":
    main()
