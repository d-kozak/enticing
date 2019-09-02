#!/bin/bash
ENTICING_HOME=$("$(dirname "$BASH_SOURCE")"/get_enticing_home.sh)
retval=$?
if [[ "$retval" -ne "0" ]] ; then
  exit $retval
fi
cd "${ENTICING_HOME}"/lib || exit 1
rm -rf mg4j-bin deps.tar.gz mg4j.tar.gz tmp
mkdir -p mg4j-bin tmp
wget -O deps.tar.gz http://mg4j.di.unimi.it/mg4j-big-deps.tar.gz
tar -C mg4j-bin -xvf deps.tar.gz
wget -O mg4j.tar.gz http://mg4j.di.unimi.it/mg4j-big-5.4.3-bin.tar.gz
tar -C tmp -xvf mg4j.tar.gz
mv tmp/mg4j-big-5.4.3/mg4j-big-5.4.3.jar mg4j-bin/mg4j-big-5.4.3.jar
rm -rf deps.tar.gz mg4j.tar.gz tmp
