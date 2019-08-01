package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.dto.IndexServerRequestData
import cz.vutbr.fit.knot.enticing.dto.config.SearchConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientType
import cz.vutbr.fit.knot.enticing.query.processor.FuelQueryExecutor
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher

fun startRemoteClient(config: ConsoleClientType.RemoteIndex, searchConfig: SearchConfig, input: Sequence<String>) {

    val dispatcher = QueryDispatcher(FuelQueryExecutor())

    for (line in input) {
        val servers = config.servers
        val query = searchConfig.toTemplateQuery().copy(query = line)
        val resultMap = dispatcher.dispatchQuery(query, servers.map { IndexServerRequestData(it) })

        for ((server, results) in resultMap) {
            println("Results from $server")

            for (result in results) {
                if (result.isSuccess) {
                    printResult(result.value)
                } else {
                    result.exception.printStackTrace()
                }
            }
        }
    }
}
