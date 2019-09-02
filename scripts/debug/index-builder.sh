#!/bin/bash
ENTICING_HOME=$("$(dirname "$BASH_SOURCE")"/../utils/get_enticing_home.sh)
retval=$?
if [[ "$retval" -ne "0" ]] ; then
  exit $retval
fi
"${ENTICING_HOME}"/bin/index-builder "${ENTICING_HOME}"/index-builder/src/test/resources/indexer.config.kts "col1" "${ENTICING_HOME}"/data/mg4j "${ENTICING_HOME}"/data/indexed
