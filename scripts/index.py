import glob
import os
import subprocess
import sys
import threading
import time


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


def execute_local_indexing(ouput_dir):
    if not os.path.exists(ouput_dir):
        os.makedirs(ouput_dir)


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


def create_remote_dir(server, directory):
    print(f'creating remote dir {directory}')
    cmd = f'ssh xkozak15@{server} mkdir -p {directory}'
    proc = execute_command(cmd)
    print(proc.stdout)
    print(proc.stderr)
    proc.check_returncode()


def execute_command(command):
    return subprocess.run(
        command.split(),
        stdout=subprocess.PIPE,
        check=True)


def count_size(files):
    return sum(map(lambda file: os.path.getsize(file) / 1_000_000, files))


def send_files(server, server_batch, output_dir):
    create_remote_dir(server, output_dir)
    print(f"{server} : {count_size(server_batch)} MB : {server_batch} ")
    collections = [f"{server}-{x}" for x in range(8)]
    collection_batches = split_files(server_batch, collections)

    for collection, collection_batch in collection_batches.items():
        print(f"\t{collection} : {count_size(collection_batch)} MB : {collection_batch}")
        cmd = f"scp {' '.join(collection_batch)} xkozak15@{server}:{output_dir}"
        print(f"\t\texecuting command '{cmd}'")


def measure(block):
    start_time = time.time()
    res = block()
    duration = time.time() - start_time
    return duration, res


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
        print("all threads finished")


def main():
    input_files, servers, output_dir = handle_args(sys.argv[1:])
    if not input_files:
        raise ValueError("No mg4j files found")
    print(f"distributing {len(input_files)} files over {len(servers)} servers({servers})")
    server_batches = split_files(input_files, servers)

    duration, _ = measure(lambda: distribute_files(server_batches, output_dir))

    print(f"distribution took {duration} seconds")


if __name__ == "__main__":
    main()
