import configparser
import sys

from typing import Dict, List

conf_req = Dict[str, Dict[str, str]]

DEFAULT_STRUCTURE: conf_req = {
    "common": {
        "username": "username on the servers",
        "local_home": "local git repository of the project",
        "remote_home": "remote git repository of the project",
        "name": "name of the corpus",
        "server": "server on which the webserver will be started",
        "files": "location of the input mg4j files",
        "servers_file": "list of servers which should handle the corpus",
        "collections_per_server": "how many collections should be created on each server",
        "mg4j_dir": "where to distribute the files locally on the server"
    },
    "index-builder": {
        "config": "location of the config script"
    },
    "index-server": {
        "screen_name": "name of the screen in which the index server should be started",
        "port": "port on which the index-server should be started",
        "config": "location of the config script",
        "log": "where the logs shoulds be written"
    },
    "webserver": {
        "screen_name": "name of the screen in which the index server should be started",
        "server": "server where the webserver should be started",
        "port": "port on which the webserver should be started"
    },
    "debug": {
        "level": "log level"
    }
}


def verify_config(config: configparser.ConfigParser, requirements: conf_req = DEFAULT_STRUCTURE) -> List[str]:
    errors = []
    for section in requirements.keys():
        requirement_block = requirements[section]
        if section not in config:
            errors.append(f"Section '{section}' is not in configuration")
        else:
            section_block = config[section]
            for item in requirement_block.keys():
                if item not in section_block:
                    errors.append(
                        f"Item '{item}' in section '{section}' is not in configuration, it should contain '{requirement_block[item]}'")
    if errors:
        for error in errors:
            print(error, file=sys.stderr)
    return errors
