#!/bin/bash
ENTICING_HOME=$("$(dirname "$BASH_SOURCE")"/../utils/get_enticing_home.sh)
retval=$?
if [[ "$retval" -ne "0" ]]; then
  exit $retval
fi
cd "${ENTICING_HOME}/scripts" || exit 1
"${ENTICING_HOME}"/bin/console-client "${ENTICING_HOME}"/console-client/src/test/resources/client.config.local.kts
