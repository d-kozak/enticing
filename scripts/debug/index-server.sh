#!/bin/bash
ENTICING_HOME=$("$(dirname "$BASH_SOURCE")"/../utils/get_enticing_home.sh)
retval=$?
if [[ "$retval" -ne "0" ]] ; then
  exit $retval
fi
"${ENTICING_HOME}"/bin/index-server --config.file="${ENTICING_HOME}"/index-server/src/test/resources/client.config.kts --mg4j.files=foo --index.directory=bar  --server.port=8001