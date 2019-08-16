#!/bin/bash
[[ -d "$ENTICING_HOME" ]] || { echo "ENTICING_HOME not defined" >&2; exit 1; }
java -jar "${ENTICING_HOME}"/lib/webserver-0.0.1-SNAPSHOT.jar