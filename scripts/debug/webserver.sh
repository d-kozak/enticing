#!/bin/bash
ENTICING_HOME=$("$(dirname "$BASH_SOURCE")"/../utils/get_enticing_home.sh)
retval=$?
if [[ "$retval" -ne "0" ]] ; then
  exit $retval
fi
export DATABASE_URL=postgres://enticing:enticing@localhost:5432/enticing
"${ENTICING_HOME}"/bin/webserver