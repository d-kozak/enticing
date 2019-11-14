import configparser

from typing import Dict, List

conf_req = Dict[str, Dict[str, str]]


def simple_compare(req: str, value: str):
    pass


def verify_config(config: configparser.ConfigParser, requirements: conf_req) -> List[str]:
    config.sections()
    return []
