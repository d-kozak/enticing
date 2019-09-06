#!/bin/bash
ENTICING_HOME=$("$(dirname "$BASH_SOURCE")"/../../scripts/utils/get_enticing_home.sh)
retval=$?
if [[ "$retval" -ne "0" ]]; then
  exit $retval
fi

cd "$ENTICING_HOME/scripts" || exit 1
python3.6 distribute_corpus.py /var/xdolez52/Zpracovani_Wikipedie/html_from_wikipedia_en_all_novid_2018-10.zim/6-mg4j/ /mnt/minerva1/nlp/projects/corpproc_search/corpproc_search /mnt/data/indexes/xkozak15/wiki
