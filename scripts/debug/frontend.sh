#!/bin/bash
ENTICING_HOME=$("$(dirname "$BASH_SOURCE")"/../utils/get_enticing_home.sh)
retval=$?
if [[ "$retval" -ne "0" ]] ; then
  exit $retval
fi
cd "${ENTICING_HOME}"/frontend && npm run start