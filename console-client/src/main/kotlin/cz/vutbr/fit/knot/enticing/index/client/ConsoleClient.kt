package cz.vutbr.fit.knot.enticing.index.client

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import java.io.File
import java.io.PrintStream
import kotlin.time.ExperimentalTime

/**
 * Main class of the client,
 * the execution is parameterized by the ConsoleClientArgs object passed in
 */
@ExperimentalTime
class ConsoleClient(val args: ConsoleClientArgs, loggerFactory: LoggerFactory) : AutoCloseable {

    private val logger = loggerFactory.logger { }

    /**
     * Which target to use
     */
    private val target: QueryTarget = when {
        args.indexServer != null -> QueryTarget.IndexServerTarget(args.indexServer!!, loggerFactory)
        args.webserver != null -> QueryTarget.WebserverTarget(args.webserver!!, args.searchSettingsId, loggerFactory)
        args.queryDispatcher != null -> QueryTarget.QueryDispatcherTarget(args.queryDispatcher!!, loggerFactory)
        else -> error("One of targets has to be enabled")
    }.also {
        logger.info("Targetting $it")
    }

    /**
     * Queries to submit
     */
    private val queries = when {
        args.query != null -> listOf(args.query!!)
        args.queryFile != null -> File(args.queryFile!!).readLines().filterNot { it.startsWith("#") }
        else -> error("One of targets has to be enabled")
    }


    /**
     * Where to save results
     */
    private val outputStream = if (args.resultFile != null) {
        logger.info("Saving results to file ${args.resultFile}")
        PrintStream(args.resultFile!!)
    } else System.out

    /**
     * Run the console client
     */
    fun exec() {
        for (query in queries) {
            logger.info("Executing query '$query'")
            val searchQuery = SearchQuery(query, snippetCount = args.snippetCount, resultFormat = args.resultFormat, textFormat = args.textFormat)
            val (results, duration) = if (args.allResults) target.collectAllResults(searchQuery) else target.submitQuery(searchQuery)
            logger.info("Finished, it took $duration")
            processResults(searchQuery, results)
        }
    }

    /**
     * Process the results and write them to the output stream
     */
    private fun processResults(query: SearchQuery, results: List<IndexServer.SearchResult>) {
        logger.info("${results.size} results returned")
        for (result in results) {
            outputStream.println(result.documentTitle)
            outputStream.println(result.url)
            when (result.payload) {
                is ResultFormat.Snippet -> outputStream.println(extractPayload(result.payload as ResultFormat.Snippet))
                is ResultFormat.IdentifierList -> {
                    (result.payload as ResultFormat.IdentifierList)
                            .list
                            .map { "${it.identifier} := ${extractPayload(it.snippet)}" }
                            .forEach { outputStream.println(it) }
                }
            }
            outputStream.println("=====")
        }
    }

    private fun extractPayload(snippet: ResultFormat.Snippet): String = when (snippet) {
        is ResultFormat.Snippet.PlainText -> snippet.content
        is ResultFormat.Snippet.Html -> snippet.content
        is ResultFormat.Snippet.StringWithMetadata -> snippet.content.toString()
        is ResultFormat.Snippet.TextUnitList -> snippet.content.toString()
    }


    override fun close() {
        if (args.resultFile != null) outputStream.close()
    }
}
