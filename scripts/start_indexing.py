import os
import sys

from utils.utils import get_enticing_home, execute_command


def handle_args(args):
    if len(args) != 3:
        raise ValueError("server_file config.kts(on servers) collection_dir(on servers)")
    if not os.path.isfile(args[0]):
        raise ValueError(f"Server file {args[0]} not found")
    return args[0], args[1], args[2]


def main():
    enticing_home = get_enticing_home()
    server_file, config, collection_dir = handle_args(sys.argv[1:])
    cmd = f'parallel-ssh -l xkozak15 -h "{server_file}" -i {enticing_home}/scripts/node/start_indexing.sh {collection_dir}'
    execute_command(cmd)


if __name__ == "__main__":
    main()
