package cz.vutbr.fit.knot.enticing.index.client

import com.github.kittinunf.fuel.httpPost
import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.result.toRawText
import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnit
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.query.processor.FuelQueryExecutor
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.flattenResults
import cz.vutbr.fit.knot.enticing.query.processor.fuel.jsonBody
import kotlinx.coroutines.runBlocking
import java.io.FileWriter
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

private val apiBasePath = "/api/v1"

private val queryExecutor = FuelQueryExecutor("$apiBasePath/query")
private val queryDispatcher = QueryDispatcher(queryExecutor, ComponentType.CONSOLE_CLIENT, SimpleStdoutLoggerFactory)

val IndexServer.SearchResult.textUnitList: List<TextUnit>
    get() = ((this.payload as ResultFormat.Snippet) as ResultFormat.Snippet.TextUnitList).content.content

@ExperimentalTime
sealed class Target(val name: String) {
    abstract fun query(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>>

    data class WebserverTarget(val address: String, val settingsId: Int) : Target("Webserver") {

        override fun query(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> {
            val address = "http://$address/api/v1/query?settings=$settingsId"
            return measureTimedValue {
                address.httpPost()
                        .jsonBody(query)
                        .responseString().third.get().toDto<WebServer.ResultList>()
                        .searchResults.map { it.toIndexServerFormat() }
            }
        }
    }

    data class QueryDispatcherTarget(val corpus: CorpusConfiguration) : Target("Dispatcher") {
        override fun query(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> {
            val nodes = corpus.indexServers.map { IndexServerRequestData(it.fullAddress) }
            val onResult: (RequestData<Map<CollectionName, Offset>>, MResult<IndexServer.IndexResultList>) -> Unit = { request, result ->
                if (result.isSuccess)
                    println("Request $request finished, got ${result.value.searchResults.size} results")
                else println("Request $request failed")
            }
            return measureTimedValue {
                queryDispatcher.dispatchQuery(query, nodes, onResult)
                        .flattenResults(query.query, SimpleStdoutLoggerFactory.namedLogger("QueryDispatcherTarget"))
                        .first.searchResults
                        .map { it.toIndexServerFormat() }
            }
        }
    }

    class IndexServerTarget(val address: String) : Target("IndexServer") {
        override fun query(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> =
                runBlocking {
                    measureTimedValue { queryExecutor.invoke(query, IndexServerRequestData(address)).value.searchResults }
                }


    }
}

@ExperimentalTime
class ConsoleClient(val args: ConsoleClientArgs, loggerFactory: LoggerFactory) : AutoCloseable {

    private val enticingConf = args.configuration

    private val corpusConf = args.corpusConfiguration

    private val logger = loggerFactory.logger { }

    private var target = if (args.useWebserver) Target.WebserverTarget(enticingConf.webserverConfiguration.fullAddress, args.searchSettingsId)
    else Target.QueryDispatcherTarget(corpusConf)


    private val resultWriter = FileWriter(args.resultFile, args.appendFiles)

    private val perfWriter = FileWriter(args.perfFile, args.appendFiles)


    private val tokenIndex = corpusConf.metadataConfiguration.indexOf("token")

    init {
        logger.info("Loaded with args $args")
        if (!args.appendFiles) {
            resultWriter.appendln("some header :) todo")
            perfWriter.appendln("targetComponent,query,millis")
        }
    }


    fun run() {
        when {
            args.query.isNotEmpty() -> runQuery(args.query)
            args.shell -> runShell()
        }
    }

    private fun runShell() {
        logger.info("Starting interactive shell")
        lineSequence()
                .withCommandHandler()
                .forEach { line -> runQuery(line) }
    }


    private fun Sequence<String>.withCommandHandler(): Sequence<String> = sequence {
        for (line in this@withCommandHandler) {
            if (line.startsWith("$"))
                handleCommand(line.substring(1))
            else yield(line)
        }
    }

    private fun handleCommand(cmd: String) {
        val parts = cmd.split(" ")
        if (parts.isEmpty()) {
            logger.warn("Cannot parse command $cmd")
        }
        when (parts[0]) {
            "webserver", "w" -> {
                logger.info("Using webserver")
                target = Target.WebserverTarget(enticingConf.webserverConfiguration.fullAddress, args.searchSettingsId)
            }
            "dispatch", "d" -> {
                logger.info("Using query dispatcher")
                target = Target.QueryDispatcherTarget(corpusConf)
            }
            "index", "i" -> {
                if (parts.size != 2) {
                    logger.warn("Index server address expected")
                    return
                }
                val address = parts[1]
                logger.info("Using index server $address")
                target = Target.IndexServerTarget(address)
            }
            else -> logger.warn("Unknown command $cmd")
        }
    }

    fun runQuery(query: String) {
        logger.info("Running query '$query'")
        try {
            val (results, duration) = target.query(SearchQuery(query))
            processResults(query, results, duration, target.name)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    private fun processResults(query: String, results: List<IndexServer.SearchResult>, duration: Duration, component: String) {
        resultWriter.appendln("Running query '$query' on $component")
        results.forEach {
            val text = it.textUnitList.toRawText(tokenIndex)
            resultWriter.appendln(text)
        }
        resultWriter.flush()
        resultWriter.appendln("Query finished, it took ${duration.inMilliseconds} ms")
        perfWriter.appendln("$component,$query,${duration.inMilliseconds}")
        perfWriter.flush()

        logger.info("Returned ${results.size} snippets, took ${duration.inMilliseconds} ms")
    }


    override fun close() {
        resultWriter.close()
        perfWriter.close()
    }
}