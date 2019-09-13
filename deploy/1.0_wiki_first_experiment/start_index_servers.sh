#!/bin/bash
ENTICING_HOME=$("$(dirname "$BASH_SOURCE")"/../../scripts/utils/get_enticing_home.sh)
retval=$?
if [[ "$retval" -ne "0" ]]; then
  exit $retval
fi

cd "$ENTICING_HOME/scripts" || exit 1

parallel-ssh -l xkozak15 -h "${ENTICING_HOME}/deploy/1.0_wiki_first_experiment/martin_limit.txt" -i "cd /mnt/minerva1/nlp/projects/corpproc_search/corpproc_search/ && /opt/anaconda3/bin/python3.6 ./scripts/node/start_server.py /mnt/data/indexes/xkozak15/wiki /mnt/minerva1/nlp/projects/corpproc_search/corpproc_search/index-server/src/test/resources/client.config.kts /mnt/data/indexes/xkozak15/wiki/indexserver.log"