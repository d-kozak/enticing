#!/bin/bash
ENTICING_HOME=$("$(dirname "$BASH_SOURCE")"/../../scripts/utils/get_enticing_home.sh)
retval=$?
if [[ "$retval" -ne "0" ]]; then
  exit $retval
fi

webserver_server="athena10.fit.vutbr.cz"
webserver_screen_name="enticing-webserver"

cd "$ENTICING_HOME" || exit 1
./scripts/kill_screens.sh "$ENTICING_HOME"/deploy/1.0_wiki_first_experiment/martin_limit.txt enticing-index-server-1-wiki
ssh "xkozak15@$webserver_server" "screen -S $webserver_screen_name -X quit"
echo "All running enticing screens were killed"
