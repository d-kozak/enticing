#!/bin/bash
[[ -d "$ENTICING_HOME" ]] || { echo "ENTICING_HOME not defined" >&2; exit 1; }
export REMOTE_HOME="/mnt/minerva1/nlp/projects/corpproc_search/cloned/enticing"
parallel-ssh -l xkozak15 -h "$ENTICING_HOME"/scripts/servers/athenas.txt -i "$REMOTE_HOME/scripts/node/start_indexing.sh"
