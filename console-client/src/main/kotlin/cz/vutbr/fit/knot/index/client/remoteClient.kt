package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.dto.config.SearchConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientType
import cz.vutbr.fit.knot.enticing.query.processor.RestTemplateRequestDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.process

fun startRemoteClient(config: ConsoleClientType.RemoteIndex, searchConfig: SearchConfig, input: Sequence<String>) {

    for (line in input) {
        val servers = config.servers
        val query = searchConfig.toTemplateQuery().copy(query = line)

        val result = process(query, servers, RestTemplateRequestDispatcher())

        println("result is $result")
    }
}
