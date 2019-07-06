package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientType
import cz.vutbr.fit.knot.enticing.dto.query.*
import cz.vutbr.fit.knot.enticing.index.QueryExecutor
import cz.vutbr.fit.knot.enticing.index.initQueryExecutor

fun startLocalClient(config: ConsoleClientConfig, input: Sequence<String>) {
    val localClient = config.clientType as ConsoleClientType.LocalIndex
    val queryExecutor = initQueryExecutor(localClient.indexClientConfig)

    for (line in input) {
        executeLocally(queryExecutor, line)
    }
}

internal fun executeLocally(queryExecutor: QueryExecutor, input: String) {
    val query = SearchQuery(
            input,
            20,
            Offset(0, 0),
            TextMetadata.Predefined("none"),
            ResponseType.SNIPPET,
            ResponseFormat.HTML
    )
    val response = queryExecutor.query(query)
    println("Response $response")
    if (response.isSuccess) {
        val value = response.value
        for (match in value.matched) {
            println("doc ${match.documentTitle}")
            println("text ${match.payload}")
        }
    }
}


