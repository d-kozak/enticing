# General high level script starting the process,
# takes config file as input
import argparse
import configparser
import glob
import logging as log
import sys

from check_mg4j_files import show_all_mg4j_files
from distribute_corpus import distribute_corpus
from start_indexing import start_indexing
from utils.config_validation import verify_config
from utils.utils import execute_via_ssh, execute_parallel_ssh, execute_command


def print_config(config: configparser.ConfigParser) -> None:
    print("======Loaded configuration======")
    for section_name in config.sections():
        print('Section:', section_name)
        print('  Options:', config.options(section_name))
        for name, value in config.items(section_name):
            print('  {} = {}'.format(name, value))
        print()
    print("================================")


def read_config(content: str) -> configparser.ConfigParser:
    config = configparser.ConfigParser(interpolation=configparser.ExtendedInterpolation())
    config.read_string(content)
    return config


def parse_arguments() -> argparse.Namespace:
    parser = argparse.ArgumentParser("Enticing main run script")
    parser.add_argument("conf", type=argparse.FileType('r'), help="Configuration file")
    parser.add_argument("-s", "--show", action='store_true', help="Show how mg4j files are distributed")
    parser.add_argument("-c", "--clean", action='store_true', help="Clean mg4j directories on the servers")
    parser.add_argument("-d", "--distrib", action='store_true', help="Distribute mg4j files over servers")
    parser.add_argument("-i", "--index", action='store_true', help="Index mg4j files using index-builder")
    parser.add_argument("-r", "--reboot", action='store_true', help="Reboot index servers and webserver")
    parser.add_argument("-w", "--webserver", action='store_true', help="Reboot webserver only")
    parser.add_argument("-k", "--kill", action='store_true', help="Kill all screens")
    namespace = parser.parse_args()
    if not any(
            [namespace.clean, namespace.show, namespace.distrib, namespace.index, namespace.reboot, namespace.webserver,
             namespace.kill]):
        print("At least one operation is required", file=sys.stderr)
        parser.print_help()
        sys.exit(1)

    return namespace


def execute_clean(conf: configparser.ConfigParser) -> None:
    servers = open(conf["common"]["servers_file"]).read().split("\n")
    output_dir = conf["common"]["mg4j_dir"]
    user = conf["common"]["username"]
    log.info(f'Cleaning dir {output_dir} on servers {servers}')
    for server in servers:
        log.info(f'\t{server}')
        cmd = f'rm -rf {output_dir}/*'
        execute_via_ssh(server, user, cmd)
    pass


def execute_distrib(conf: configparser.ConfigParser) -> None:
    input_files = glob.glob(conf["common"]["files"] + "/*.mg4j")
    output_dir = conf["common"]["mg4j_dir"]
    servers = open(conf["common"]["servers_file"]).read().split("\n")
    distribute_corpus(input_files, output_dir, servers)


def execute_show(conf: configparser.ConfigParser) -> None:
    mg4j_dir = conf["common"]["mg4j_dir"]
    servers = conf["common"]["servers_file"]
    show_all_mg4j_files(mg4j_dir, servers)


def execute_start_indexing(conf: configparser.ConfigParser) -> None:
    mg4j_dir = conf["common"]["mg4j_dir"]
    kts_config = conf["index-builder"]["config"]
    user = conf["common"]["username"]
    servers = conf["common"]["servers_file"]
    enticing_home = conf["common"]["enticing_home"]
    start_indexing(mg4j_dir, kts_config, enticing_home, servers, user)


def execute_index_server_start(conf: configparser.ConfigParser) -> None:
    servers = conf["common"]["servers_file"]
    user = conf["common"]["username"]
    index_screen = conf["index-server"]["screen_name"]
    index_port = conf["index-server"]["port"]
    index_conf = conf["index-server"]["config"]
    index_log = conf["index-server"]["log"]
    enticing_home = conf["common"]["enticing_home"]
    mg4j_dir = conf["common"]["mg4j_dir"]
    cmd = f"/opt/anaconda3/bin/python3.6 {enticing_home}/scripts/node/start_server.py {mg4j_dir} {index_conf} {index_screen} {index_port} {index_log}"
    execute_parallel_ssh(servers, user, cmd)


def execute_index_server_kill(conf: configparser.ConfigParser) -> None:
    servers = conf["common"]["servers_file"]
    user = conf["common"]["username"]
    index_screen = conf["index-server"]["screen_name"]
    cmd = f"screen -S {index_screen} -X quit"
    execute_parallel_ssh(servers, user, cmd)


def execute_webserver_start(conf: configparser.ConfigParser) -> None:
    user = conf["common"]["username"]
    enticing_home = conf["common"]["enticing_home"]
    webserver_server = conf["webserver"]["server"]
    webserver_screen_name = conf["webserver"]["screen_name"]
    webserver_log = conf["webserver"]["log"]
    cmd = f"cd {enticing_home} && screen -S {webserver_screen_name} -d -m ./bin/webserver && screen -S {webserver_screen_name} -X logfile {webserver_log} && screen -S {webserver_screen_name} -X log"
    execute_via_ssh(webserver_server, user, cmd)


def execute_webserver_kill(conf: configparser.ConfigParser) -> None:
    user = conf["common"]["username"]
    webserver_server = conf["webserver"]["server"]
    webserver_screen_name = conf["webserver"]["screen_name"]
    cmd = f"screen -S {webserver_screen_name} -X quit"
    execute_via_ssh(webserver_server, user, cmd)


def prepare_jars(conf: configparser.ConfigParser) -> None:
    execute_command("gradle buildAll")
    user = conf["common"]["username"]
    webserver_server = conf["webserver"]["server"]
    enticing_home = conf["common"]["enticing_home"]
    cmd = f"scp lib/*.jar {user}@{webserver_server}:{enticing_home}/lib/"
    execute_command(cmd)


def main():
    args = parse_arguments()
    conf = read_config(args.conf.read())
    errors = verify_config(conf)
    log.basicConfig(level=int(conf["debug"]["level"]), format='%(asctime)s - %(levelname)s - %(message)s')
    if errors:
        sys.exit(1)
    print_config(conf)

    if args.kill or args.reboot:
        execute_index_server_kill(conf)
        execute_webserver_kill(conf)

    if args.clean:
        execute_clean(conf)

    if args.distrib:
        execute_distrib(conf)

    if args.show:
        execute_show(conf)

    if args.index:
        execute_start_indexing(conf)

    if args.webserver:
        prepare_jars(conf)
        execute_webserver_kill(conf)
        execute_webserver_start(conf)

    if args.reboot:
        prepare_jars(conf)
        execute_index_server_start(conf)
        execute_webserver_start(conf)


if __name__ == "__main__":
    main()
