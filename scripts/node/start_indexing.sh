#!/bin/bash
[[ -d "$ENTICING_HOME" ]] || { echo "ENTICING_HOME not defined" >&2; exit 1; }
MEM=$("$ENTICING_HOME"/scripts/helpers/print_mem.sh)
echo "I've got $MEM mb of free ram"