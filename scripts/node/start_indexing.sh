#!/bin/bash
[[ -d "$ENTICING_HOME" ]] || { echo "ENTICING_HOME not defined, trying to configure it" >&2; export ENTICING_HOME=realpath "$BASH_SOURCE/../.."; echo "ENTICING_HOME set to $ENTICING_HOME"; }
MEM=$("$ENTICING_HOME"/scripts/helpers/print_mem.sh)
echo "I've got $MEM mb of free ram"
echo "REMOTE HOME IS $REMOTE_HOME"