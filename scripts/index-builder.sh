#!/bin/bash
[[ -d "$ENTICING_HOME" ]] || { echo "ENTICING_HOME not defined" >&2; exit 1; }
java -cp "${ENTICING_HOME}/lib/*:${ENTICING_HOME}/lib/mg4j-bin/*" cz.vutbr.fit.knot.enticing.indexer.MainKt "$@"