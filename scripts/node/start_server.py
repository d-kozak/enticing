import os
import sys

sys.path.append(os.path.dirname(os.path.dirname(__file__)))

import json
import logging as log

from utils.utils import start_screen, read_default_config, init_logging, get_enticing_home

config = read_default_config()


def handle_args(args):
    if len(args) != 5:
        raise ValueError("format: collection_dir kts_config name port_number logfile")
    collection_dir = args[0]
    if not os.path.isdir(collection_dir):
        raise ValueError(f"{collection_dir} is not a directory")
    kts_config = args[1]
    if not os.path.isfile(kts_config):
        raise ValueError(f"{kts_config} is not a real file")
    return collection_dir, kts_config, args[2], args[3], args[4]


def inspect_collection_dir(collection_dir):
    content = os.listdir(collection_dir)
    if "indexed" not in content:
        raise ValueError("indexed directory(for mg4j metadata) not found")
    mg4j_dirs = [file for file in content if os.path.isdir(os.path.join(collection_dir, file)) and file != "indexed"]
    if not mg4j_dirs:
        raise ValueError(f"no mg4j collection dirs found in {collection_dir}")
    collection_config = {}
    for mg4j_dir in mg4j_dirs:
        output_dir = os.path.join(os.path.join(collection_dir, "indexed"), mg4j_dir)
        if not os.path.isdir(output_dir):
            log.error(f"Could not find dir {output_dir} for collection {mg4j_dir}, skipping")
            continue
        collection_config[mg4j_dir] = [os.path.join(collection_dir, mg4j_dir), output_dir]
    return collection_config


def serialize_config(config):
    return json.dumps(config).replace(' ', '')


def main():
    init_logging(log.DEBUG)
    collection_dir, kts_config, name, port_number, logfile = handle_args(sys.argv[1:])
    enticing_home = get_enticing_home()
    collection_config = inspect_collection_dir(collection_dir)
    start_screen(
        f'{enticing_home}/bin/index-server {kts_config} {serialize_config(collection_config)} --server.port={port_number}',
        name=name, logfile=logfile)


if __name__ == "__main__":
    main()
