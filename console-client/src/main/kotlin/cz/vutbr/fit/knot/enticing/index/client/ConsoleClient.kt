package cz.vutbr.fit.knot.enticing.index.client

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import java.io.File
import java.io.PrintStream
import kotlin.time.ExperimentalTime

@ExperimentalTime
class ConsoleClient(val args: ConsoleClientArgs, loggerFactory: LoggerFactory) : AutoCloseable {
    private val logger = loggerFactory.logger { }


    private val target: QueryTarget = when {
        args.indexServer != null -> QueryTarget.IndexServerTarget(args.indexServer!!)
        args.webserver != null -> QueryTarget.WebserverTarget(args.webserver!!, args.searchSettingsId)
        args.queryDispatcher != null -> QueryTarget.QueryDispatcherTarget(args.queryDispatcher!!)
        else -> error("One of targets has to be enabled")
    }.also {
        logger.info("Targetting $it")
    }

    private val queries = when {
        args.query != null -> listOf(args.query!!)
        args.queryFile != null -> File(args.queryFile!!).readLines().filterNot { it.startsWith("#") }
        else -> error("One of targets has to be enabled")
    }

    private val resultStream = if (args.resultFile != null) {
        logger.info("Saving results to file ${args.resultFile}")
        PrintStream(args.resultFile!!)
    } else System.out

    fun exec() {
        for (query in queries) {
            logger.info("Executing query '$query'")
            val searchQuery = SearchQuery(query, snippetCount = args.snippetCount, resultFormat = args.resultFormat, textFormat = args.textFormat)
            val (results, duration) = if (args.allResults) target.getAll(searchQuery) else target.submit(searchQuery)
            logger.info("Finished, it took $duration")
            processResults(searchQuery, results)
        }
    }

    private fun processResults(query: SearchQuery, results: List<IndexServer.SearchResult>) {
        results.forEach { println(it) }
    }


    override fun close() {
        if (args.resultFile != null) resultStream.close()
    }
}
