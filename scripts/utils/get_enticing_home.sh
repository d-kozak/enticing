#!/bin/bash
if [[ -d "$ENTICING_HOME" ]] ; then
  echo "$ENTICING_HOME"
else
  echo -n "ENTICING_HOME not set, trying to infer it..." >&2
  home=$(realpath $(dirname "$BASH_SOURCE")/../..)
  if [[ -d "$home" ]] ; then
    echo " done" >&2
    echo "$home"
  else
    echo "failed to infer ENTICING_HOME" >&2
    exit 1
  fi
fi