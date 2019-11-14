# General high level script starting the process,
# takes config file as input
import argparse
import configparser
import sys


def print_config(config: configparser.ConfigParser):
    for section_name in config.sections():
        print('Section:', section_name)
        print('  Options:', config.options(section_name))
        for name, value in config.items(section_name):
            print('  {} = {}'.format(name, value))
        print()


def read_config(content: str) -> configparser.ConfigParser:
    config = configparser.ConfigParser(interpolation=configparser.ExtendedInterpolation())
    config.read_string(content)
    print_config(config)
    return config


def parse_arguments() -> argparse.Namespace:
    parser = argparse.ArgumentParser("Enticing main run script")
    parser.add_argument("conf", type=argparse.FileType('r'), help="Configuration file")
    parser.add_argument("-c", "--clean", action='store_true', help="Clean mg4j directories on the servers")
    parser.add_argument("-d", "--distrib", action='store_true', help="Distribute mg4j files over servers")
    parser.add_argument("-i", "--index", action='store_true', help="Index mg4j files using index-builder")
    parser.add_argument("-r", "--reboot", action='store_true', help="Reboot index servers and webserver")
    parser.add_argument("-k", "--kill", action='store_true', help="Kill all screens")
    namespace = parser.parse_args()
    if not (namespace.clean or namespace.distrib or namespace.index or namespace.reboot or namespace.kill):
        print("At least one operation is required", file=sys.stderr)
        parser.print_help()
        sys.exit(1)

    return namespace


def main():
    args = parse_arguments()
    print(args)
    conf = read_config(args.conf.read())
    print(conf)
    pass


if __name__ == "__main__":
    main()
