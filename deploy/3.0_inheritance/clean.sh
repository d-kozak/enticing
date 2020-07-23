#!/usr/bin/env bash
for server in $(cat ./servers.txt); do
  ssh xkozak15@$server /mnt/minerva1/nlp/homes/xkozak15/killMain.sh
done
