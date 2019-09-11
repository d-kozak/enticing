#!/bin/bash
ENTICING_HOME=$("$(dirname "$BASH_SOURCE")"/../../scripts/utils/get_enticing_home.sh)
retval=$?
if [[ "$retval" -ne "0" ]]; then
  exit $retval
fi

remote_home="/mnt/minerva1/nlp/projects/corpproc_search/corpproc_search"
webserver_server="athena10.fit.vutbr.cz"
webserver_screen_name="enticing-webserver"
webserver_logfile="/mnt/data/indexes/xkozak15/wiki/webserver.log"

cd "$ENTICING_HOME" || exit 1
./scripts/kill_screens.sh "$ENTICING_HOME"/deploy/1.0_wiki_first_experiment/martin_limit.txt
ssh "xkozak15@$webserver_server" "screen -S $webserver_screen_name -X quit"
echo "1/5 All running enticing screens were killed"
gradle buildAll || exit 1
echo "2/5 Project build finished"
jars=$(ls lib/*.jar)
scp $jars "xkozak15@$webserver_server:$remote_home/lib/" || exit 1
echo "3/5 Jars copied"
./deploy/1.0_wiki_first_experiment/start_index_servers.sh || exit 1
echo "4/5 Index servers started"
ssh "xkozak15@$webserver_server" "cd $remote_home && screen -S $webserver_screen_name -d -m ./bin/webserver && screen -S $webserver_screen_name -X logfile $webserver_logfile && screen -S $webserver_screen_name -X log" || exit 1
echo "5/5 Webserver started"
echo "FINISHED"
