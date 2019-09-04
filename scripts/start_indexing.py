import os
import sys

from utils.utils import execute_command, read_default_config, init_logging

config = read_default_config()

def handle_args(args):
    if len(args) != 4:
        raise ValueError("server_file remote_home config.kts(on servers) collection_dir(on servers)")
    if not os.path.isfile(args[0]):
        raise ValueError(f"Server file {args[0]} not found")
    return args[0], args[1], args[2], args[3]


def main():
    init_logging(int(config['debug']['level']))
    server_file, remote_home, kts_config, collection_dir = handle_args(sys.argv[1:])
    cmd = f'parallel-ssh -l xkozak15 -h {server_file} -i {remote_home}/scripts/node/start_indexing.sh {collection_dir} {kts_config}'
    proc = execute_command(cmd)
    print(proc.stdout)

if __name__ == "__main__":
    main()
