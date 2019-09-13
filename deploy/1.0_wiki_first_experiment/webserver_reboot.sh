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
ssh "xkozak15@$webserver_server" "screen -S $webserver_screen_name -X quit"
echo "1/4 Webserver screen killed"
gradle stage || exit 1
echo "2/4 Project build finished"
scp ./lib/webserver-0.0.1-SNAPSHOT.jar "xkozak15@$webserver_server:$remote_home/lib/" || exit 1
echo "3/4 Jar copied"
ssh "xkozak15@$webserver_server" "cd $remote_home && screen -S $webserver_screen_name -d -m ./bin/webserver && screen -S $webserver_screen_name -X logfile $webserver_logfile && screen -S $webserver_screen_name -X log" || exit 1
echo "4/4 Webserver started"
echo "FINISHED"
