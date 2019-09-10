#!/bin/bash
ENTICING_HOME=$("$(dirname "$BASH_SOURCE")"/../utils/get_enticing_home.sh)
retval=$?
if [[ "$retval" -ne "0" ]]; then
  exit $retval
fi
"${ENTICING_HOME}"/bin/index-server "${ENTICING_HOME}"/index-server/src/test/resources/client.config.kts "{\"col1\":[\"${ENTICING_HOME}/data/mg4j\",\"${ENTICING_HOME}/data/indexed\"]}" --server.port=8001
