#!/bin/bash
[[ -d "$ENTICING_HOME" ]] || {
  echo "ENTICING_HOME not defined" >&2
  exit 1
}
[[ -n "$1" ]] || {
  echo "Expecting a server file as argument" >&2
  exit 1
}
[[ -n "$2" ]] || {
  echo "Expecting screen name" >&2
  exit 1
}

parallel-ssh -l xkozak15 -h "$1" -i "screen -S $2 -X quit"
