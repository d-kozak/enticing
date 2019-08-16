#!/bin/bash
[[ -d "$ENTICING_HOME" ]] || { echo "ENTICING_HOME not defined" >&2; exit 1; }
export DATABASE_URL=postgres://enticing:enticing@localhost:5432/enticing
"${ENTICING_HOME}"/scripts/webserver.sh