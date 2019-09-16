#!/bin/bash
ssh xkozak15@athena10.fit.vutbr.cz grep "admin" /mnt/data/indexes/xkozak15/wiki/webserver.log | tail -n 1
