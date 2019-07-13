package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.dto.config.SearchConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientType
import cz.vutbr.fit.knot.enticing.query.processor.FuelRequestDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher

fun startRemoteClient(config: ConsoleClientType.RemoteIndex, searchConfig: SearchConfig, input: Sequence<String>) {

    val dispatcher = QueryDispatcher(FuelRequestDispatcher())

    for (line in input) {
        val servers = config.servers
        val query = searchConfig.toTemplateQuery().copy(query = line)
        val resultMap = dispatcher.dispatchQuery(query, servers)

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
