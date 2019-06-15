import glob
import os
import sys


def handle_args(args):
    if len(args) > 2 or len(args) == 0:
        raise ValueError("server list and input directory expected")

    input_dir = args[0]
    if not os.path.isdir(input_dir):
        raise ValueError("input_dir is not a directory")

    if len(args) == 2:
        with open(args[1]) as file:
            servers = file.read().split("\n")
            return input_dir, servers
    else:
        return input_dir, False


def split_between_servers(input_files, servers):
    per_server = len(input_files) // len(servers)

    res = {}
    for i in range(len(servers)):
        res[servers[i]] = input_files[i * per_server: (i + 1) * per_server]

    leftovers = len(input_files) % len(servers)
    if leftovers > 0:
        extra_per_server = leftovers // len(servers)

        i = len(servers) * per_server
        for server in servers:
            res[server] = res[server] + input_files[i:i + extra_per_server]
            i += extra_per_server
            if i < len(input_files):
                res[server].append(input_files[i])
                i += 1
            else:
                break

    return res


def execute_locally(input_files):
    print(input_files)
    raise ValueError("Not implemented yet")


def execute_distributed(servers):
    print(servers)
    raise ValueError("Not implemented yet")


def main():
    input_dir, servers = handle_args(sys.argv[1:])
    input_files = glob.glob(input_dir + "/*.mg4j")
    if not input_files:
        raise ValueError("No mg4j files found in " + input_dir)

    if servers:
        distributed = split_between_servers(input_files, servers)
        execute_distributed(distributed)
    else:
        execute_locally(input_files)


if __name__ == "__main__":
    main()
