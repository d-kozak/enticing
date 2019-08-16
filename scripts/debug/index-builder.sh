#!/bin/bash
[[ -d "$ENTICING_HOME" ]] || { echo "ENTICING_HOME not defined" >&2; exit 1; }
"${ENTICING_HOME}"/scripts/index-builder.sh "${ENTICING_HOME}"/index-builder/src/test/resources/indexer.config.kts "${ENTICING_HOME}"/data/mg4j "${ENTICING_HOME}"/data/indexed
