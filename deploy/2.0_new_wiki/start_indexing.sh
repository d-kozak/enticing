#!/bin/bash
ENTICING_HOME=$("$(dirname "$BASH_SOURCE")"/../../scripts/utils/get_enticing_home.sh)
retval=$?
if [[ "$retval" -ne "0" ]]; then
  exit $retval
fi

cd "$ENTICING_HOME/scripts" || exit 1
python3.6 ./start_indexing.py ../deploy/2.0_new_wiki/servers.txt /mnt/minerva1/nlp/projects/corpproc_search/corpproc_search /mnt/minerva1/nlp/projects/corpproc_search/corpproc_search/deploy/2.0_new_wiki/indexer.config.kts /mnt/data/indexes/xkozak15/new_wiki
