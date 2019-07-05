package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.dto.query.*
import cz.vutbr.fit.knot.enticing.index.QueryExecutor
import cz.vutbr.fit.knot.enticing.index.config.dsl.IndexClientConfig
import cz.vutbr.fit.knot.enticing.index.config.executeScript
import cz.vutbr.fit.knot.enticing.index.initQueryExecutor

fun handleArguments(args: Array<String>): IndexClientConfig {
    args.size != 1 && throw IllegalArgumentException("Expecting exactly one argument, config file")
    return executeScript(args[0])
}

fun main(args: Array<String>) {
    val config = handleArguments(args)

    val queryExecutor = initQueryExecutor(config)

    val inputSequence = sequence<String> {
        println("Engine started")
        print("query>")

        var input = readLine()
        while (input != null && input != "exit") {
            yield(input)
            print("\nquery>")
            input = readLine()
        }
    }

    responseLoop(queryExecutor, inputSequence)
}

fun responseLoop(queryExecutor: QueryExecutor, inputSequence: Sequence<String>) {
    for (input in inputSequence) {
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
}

