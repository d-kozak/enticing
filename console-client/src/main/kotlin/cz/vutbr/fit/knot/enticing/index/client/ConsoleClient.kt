package cz.vutbr.fit.knot.enticing.index.client

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.result.toRawText
import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnit
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import java.io.File
import java.io.FileWriter
import java.util.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime


val IndexServer.SearchResult.textUnitList: List<TextUnit>
    get() = ((this.payload as ResultFormat.Snippet) as ResultFormat.Snippet.TextUnitList).content.content


@ExperimentalTime
class ConsoleClient(val args: ConsoleClientArgs, loggerFactory: LoggerFactory) : AutoCloseable {

    private val enticingConf = args.configuration

    private val corpusConf = args.corpusConfiguration

    private val logger = loggerFactory.logger { }

    private var target = if (args.useWebserver) QueryTarget.WebserverTarget(enticingConf.webserverConfiguration.fullAddress, args.searchSettingsId)
    else QueryTarget.QueryDispatcherTarget(corpusConf)


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
            args.queryFile.isNotEmpty() -> runFromFile(args.queryFile)
            args.shell -> runShell()
            else -> {
                logger.warn("No option specified, starting the shell as a default")
                runShell()
            }
        }
    }

    private fun runFromFile(queryFile: String) {
        for (line in File(queryFile).readLines())
            if (!line.startsWith("#"))
                runQuery(line)

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

                if (parts.size >= 2) {
                    val address = parts[1]
                    var port = if (parts.size >= 3) parts[2].toIntOrNull() else enticingConf.webserverConfiguration.port
                    if (port == null) {
                        logger.warn("${parts[2]} is not a valid port number")
                        port = enticingConf.webserverConfiguration.port
                    }
                    enticingConf.webserverConfiguration.address = address
                    enticingConf.webserverConfiguration.port = port

                }
                logger.info("setting webserver conf to ${enticingConf.webserverConfiguration}")
                target = QueryTarget.WebserverTarget(enticingConf.webserverConfiguration.fullAddress, args.searchSettingsId)
            }
            "dispatch", "d" -> {
                logger.info("Using query dispatcher")
                target = QueryTarget.QueryDispatcherTarget(corpusConf)
            }
            "index", "i" -> {
                if (parts.size != 2) {
                    logger.warn("Index server address expected")
                    return
                }
                val address = parts[1]
                logger.info("Using index server $address")
                target = QueryTarget.IndexServerTarget(address)
            }
            else -> logger.warn("Unknown command $cmd")
        }
    }

    private fun runQuery(query: String) {
        logger.info("Running query '$query'")
        try {
            val (results, duration) = target.query(SearchQuery(query, uuid = UUID.randomUUID()))
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