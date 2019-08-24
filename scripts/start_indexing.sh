#!/bin/bash
[[ -d "$ENTICING_HOME" ]] || { echo "ENTICING_HOME not defined" >&2; exit 1; }
parallel-ssh -l xkozak15 -h "$ENTICING_HOME"/scripts/servers/athenas.txt -i "uname -a"
