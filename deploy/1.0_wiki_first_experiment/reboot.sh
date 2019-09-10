#!/bin/bash
ENTICING_HOME=$("$(dirname "$BASH_SOURCE")"/../../scripts/utils/get_enticing_home.sh)
retval=$?
if [[ "$retval" -ne "0" ]]; then
  exit $retval
fi

cd "$ENTICING_HOME" || exit 1
./scripts/kill_screens.sh "$ENTICING_HOME"/deploy/1.0_wiki_first_experiment/martin_limit.txt
echo "1/ All running screens were killed"
gradle buildAll || exit 1
echo "2/ Project build finished"
jars=$(ls lib/*.jar)
scp $jars xkozak15@athena10.fit.vutbr.cz:/mnt/minerva1/nlp/projects/corpproc_search/corpproc_search/lib/
echo "3/ Jars copied"
./deploy/1.0_wiki_first_experiment/start_index_servers.sh
echo "4/ Index servers started"
