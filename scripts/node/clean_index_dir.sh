#!/bin/bash
[[ -d "$ENTICING_HOME" ]] || {
  echo "ENTICING_HOME not defined, trying to configure it" >&2
  export ENTICING_HOME=$(realpath $(dirname "$BASH_SOURCE")/../..)
  echo "ENTICING_HOME set to $ENTICING_HOME"
}
[[ -d "$1" ]] || {
  echo "EXPECTING a directory with collections to process, '$1' is not a local direcotory" >&2
  exit 1
}
COL_DIR="$1"
cd "$COL_DIR" || {
  echo "Could not go to the indexes directory" >&2
  exit 1
}
COLS=$(ls -d *_indexed)
for junk in $COLS
do
  echo "deleting junk dir $junk"
  rm -rf "$junk"
done
