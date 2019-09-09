#!/usr/bin/env bash
# todo fix encapsulate commands in ""
alias client-local=java -cp "*:mg4j-bin/*" cz.vutbr.fit.knot.enticing.index.client.MainKt ../console-client/src/test/resources/client.config.local.kts
alias client-local=java -cp "*:mg4j-bin/*" cz.vutbr.fit.knot.enticing.index.client.MainKt ../console-client/src/test/resources/client.config.remote.kts
alias index-builder=java -cp "*:mg4j-bin/*" cz.vutbr.fit.knot.enticing.indexer.MainKt ../index-builder/src/test/resources/indexer.config.kts ../data/mg4j ../data/indexed
alias webserver=java -jar webserver-0.0.1-SNAPSHOT.jar
alias index-client=java -jar index-server-0.0.1-SNAPSHOT.jar --config.file=../index-server/src/test/resources/client.config.kts --index.directory=../data/indexed --mg4j.files=../data/mg4j/cc1.mg4j,../data/mg4j/cc2.mg4j,../data/mg4j/cc3.mg4j,../data/mg4j/small.mg4j --server.port=8001
