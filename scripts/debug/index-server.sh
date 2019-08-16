#!/bin/bash
[[ -d "$ENTICING_HOME" ]] || { echo "ENTICING_HOME not defined" >&2; exit 1; }
"${ENTICING_HOME}"/scripts/index-server.sh --config.file="${ENTICING_HOME}"/index-server/src/test/resources/client.config.kts --mg4j.files=foo --index.directory=bar  --server.port=8001