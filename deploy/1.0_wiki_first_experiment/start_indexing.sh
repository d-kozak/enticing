#!/bin/bash
ENTICING_HOME=$("$(dirname "$BASH_SOURCE")"/../../scripts/utils/get_enticing_home.sh)
retval=$?
if [[ "$retval" -ne "0" ]]; then
  exit $retval
fi

cd "$ENTICING_HOME/scripts" || exit 1
python3.6 ./start_indexing.py  ./servers/all.txt /mnt/minerva1/nlp/projects/corpproc_search/corpproc_search /mnt/minerva1/nlp/projects/corpproc_search/corpproc_search/index-builder/src/test/resources/indexer.config.kts /mnt/data/indexes/xkozak15/wiki