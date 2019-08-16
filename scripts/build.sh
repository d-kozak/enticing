#!/bin/bash
[[ -d "$ENTICING_HOME" ]] || { echo "ENTICING_HOME not defined" >&2; exit 1; }
cd "${ENTICING_HOME}" || exit 1
gradle buildAll