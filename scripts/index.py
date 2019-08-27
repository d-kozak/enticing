import glob
import os
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


def split_files(input_files, servers):
    extra = len(input_files) % len(servers)
    per_server = len(input_files) // len(servers)
    batches = {}
    start = 0
    for i, server in enumerate(servers):
        count = per_server + 1 if i < extra else per_server
        batches[server] = input_files[start:start + count]
        start += count
    return batches


def send_files(server, batch):
    total_size = sum(map(lambda file: os.path.getsize(file) / 1_000_000, batch))
    print(f"{server} : {total_size} MB : {batch} ")

    time.sleep(1)
    pass


def measure(block):
    start_time = time.time()
    res = block()
    duration = time.time() - start_time
    return duration, res


def distribute_files(server_batches):
    threads = []
    try:
        for server, batch in server_batches.items():
            t = threading.Thread(target=send_files, args=(server, batch))
            t.start()
            threads.append(t)
    finally:
        for thread in threads:
            thread.join()
        print("all threads finished")
    pass


def main():
    input_files, servers, output_dir = handle_args(sys.argv[1:])
    if not input_files:
        raise ValueError("No mg4j files found")
    print(f"distributing {len(input_files)} files over {len(servers)} servers({servers})")
    server_batches = split_files(input_files, servers)

    duration, _ = measure(lambda: distribute_files(server_batches))

    print(f"distribution took {duration} seconds")


if __name__ == "__main__":
    main()
