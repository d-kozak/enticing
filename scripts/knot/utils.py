import logging as log
import os
import subprocess
import sys
import time


def measure(block, print_duration=False, name=""):
    start_time = time.time()
    res = block()
    duration = time.time() - start_time
    if print_duration:
        log.info(f'{name} took {duration} seconds')
    return res, duration


def count_size(files):
    return sum(map(lambda file: os.path.getsize(file) / 1_000_000, files))


def create_remote_dir(server, directory, username):
    log.debug(f'creating remote dir {directory}')
    cmd = f'ssh {username}@{server} mkdir -p {directory}'
    execute_command(cmd)


def execute_command(command, print_command=True, print_stdout=True, print_stderr=True, check_ret_val=True):
    if print_command:
        log.debug(f"Executing '{command}'")
    proc = subprocess.run(
        command.split(),
        stdout=subprocess.PIPE,
        check=True)
    if print_stdout and proc.stdout:
        log.info("process stdout:")
        for line in proc.stdout.split("\n"):
            print(line)
    if print_stderr and proc.stderr:
        log.error("process stderr:")
        for line in proc.stderr.split("\n"):
            print(line, file=sys.stderr)
    if check_ret_val:
        proc.check_returncode()
    return proc
