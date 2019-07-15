package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.config.SearchConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientType
import cz.vutbr.fit.knot.enticing.index.query.QueryExecutor
import cz.vutbr.fit.knot.enticing.index.query.initQueryExecutor

fun startLocalClient(config: ConsoleClientType.LocalIndex, searchConfig: SearchConfig, input: Sequence<String>) {
    val queryExecutor = initQueryExecutor(config.indexClientConfig)

    for (line in input) {
        executeLocally(queryExecutor, line)
    }
}

internal fun executeLocally(queryExecutor: QueryExecutor, input: String) {
    val query = SearchQuery(
            input,
            20,
            Offset(0, 0),
            TextMetadata.Predefined("all"),
            ResponseType.FULL,
            ResponseFormat.ANNOTATED_TEXT
    )
    val response = queryExecutor.query(query)
    println("Response $response")
    if (response.isSuccess) {
        printResult(response.value)
    } else {
        response.exception.printStackTrace()
    }
}


