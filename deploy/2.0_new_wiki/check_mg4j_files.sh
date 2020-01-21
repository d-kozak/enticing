#!/bin/bash
ENTICING_HOME=$("$(dirname "$BASH_SOURCE")"/../../scripts/utils/get_enticing_home.sh)
retval=$?
if [[ "$retval" -ne "0" ]]; then
  exit $retval
fi

cd "$ENTICING_HOME/scripts" || exit 1

python3.6 check_mg4j_files.py ../deploy/2.0_new_wiki/servers.txt /mnt/data/indexes/xkozak15/new_wiki