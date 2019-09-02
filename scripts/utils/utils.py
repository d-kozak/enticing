import logging as log
import os
import subprocess
import sys
import time


def get_enticing_home():
    if "ENTICING_HOME" in os.environ:
        home = os.environ["ENTICING_HOME"]
        if not os.path.isfile(home):
            raise ValueError(f"ENTICING HOME does not point to a directory, value : {home}")
        return home
    script_dir = get_script_path()
    return os.path.dirname(script_dir)


def get_script_path():
    return os.path.dirname(os.path.realpath(sys.argv[0]))


def measure(block, print_duration=False, name=""):
    start_time = time.time()
    res = block()
    duration = time.time() - start_time
    if print_duration:
        log.info(f'{name} took {duration} seconds')
    return res, duration


def count_size(files):
    return sum(map(lambda file: os.path.getsize(file) / 1_000_000, files))


def create_remote_dir(server, username, directory):
    log.debug(f'creating remote dir {directory}')
    cmd = f'mkdir -p {directory}'
    return execute_via_ssh(server, username, cmd)


def execute_via_ssh(server, username, cmd):
    cmd = f'ssh {username}@{server} {cmd}'
    return execute_command(cmd)


def execute_command(command, print_stdout=True, print_stderr=True, check_ret_val=True):
    log.debug(f"Executing '{command}'")
    proc = subprocess.run(
        command.split(),
        stdout=subprocess.PIPE,
        universal_newlines=True,
        check=True)
    if print_stdout and proc.stdout:
        log.debug("process stdout:")
        log.debug(proc.stdout)
    if print_stderr and proc.stderr:
        log.error("process stderr:")
        log.error(proc.stderr)
    if check_ret_val:
        proc.check_returncode()
    return proc
