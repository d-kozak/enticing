import logging as log
import sys

from utils.utils import read_default_config, init_logging, execute_parallel_ssh, execute_via_ssh

config = read_default_config()


def handle_args(args):
    if len(args) != 2:
        raise ValueError('format: server_files mg4j_dir(on servers)')
    return args[0], args[1]


def load_files(server, collection, mg4j_dir):
    cmd = f'ls -l {mg4j_dir}/{collection} || exit 0'
    proc = execute_via_ssh(server, config['user']['username'], cmd)
    lines = proc.stdout.split('\n')
    if len(lines) > 1:
        lines = lines[1:]
        splits = [line.split() for line in lines]
        return [[split[8], int(split[4]) / 1_000_000] for split in splits if len(split) > 8]
    else:
        return []


def check_mg4j_files(server, collections, mg4j_dir):
    all_files = []
    print(f'server {server}:')
    for collection in collections:
        print(f'\t collection {collection}')
        loaded_files = load_files(server, collection, mg4j_dir)
        for file, size in loaded_files:
            print(f'\t\t{file} {size} MB')
            all_files.append(file)
    return all_files


def main():
    init_logging(int(config["debug"]["level"]))
    server_file, mg4j_dir = handle_args(sys.argv[1:])
    res = execute_parallel_ssh(server_file, config["user"]["username"], f"ls {mg4j_dir} || exit 0")

    lines = res.stdout.split("\n")
    i = 0
    files = []
    while i < len(lines):
        if lines[i].startswith('['):
            if "[SUCCESS]" in lines[i]:
                server = lines[i].split(' ')[-1]
                collections = []
                i += 1
                while i < len(lines) and not lines[i].startswith('['):
                    if (server in lines[i]):
                        collections.append(lines[i])
                    i += 1
                files += check_mg4j_files(server, collections, mg4j_dir)
            else:
                log.error(f"skipping server with failed response {lines[i]}")
                i += 1
                while i < len(lines) and not lines[i].startswith('['):
                    i += 1

    print(f"Found {len(files)} files in total:")
    print(files)


if __name__ == "__main__":
    main()
