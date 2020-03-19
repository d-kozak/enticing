package cz.vutbr.fit.knot.enticing.index.client

import com.github.kittinunf.fuel.httpPost
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.WebServer
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.query.processor.fuel.jsonBody
import java.io.FileWriter
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@ExperimentalTime
class ConsoleClient(val args: ConsoleClientArgs, loggerFactory: LoggerFactory) : AutoCloseable {

    private val enticingConf = args.configuration

    private val logger = loggerFactory.logger { }

    private var useWebserver = args.useWebserver

    private var settingsId = args.searchSettingsId

    private val resultWriter = FileWriter(args.resultFile, true)

    private val perfWriter = FileWriter(args.perfFile, true)

    init {
        logger.info("Loaded with args $args")
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
                if (parts.size != 2) {
                    logger.warn("Command webserver, boolean argument expected")
                    return
                }
                useWebserver = parts[1].toBoolean()
                logger.info("Use webserver : $useWebserver")
            }
            else -> logger.warn("Unknown command $cmd")
        }
    }

    fun runQuery(query: String) {
        logger.info("Running query '$query'")
        try {
            if (useWebserver) {
                webserverQuery(query)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    private fun webserverQuery(query: String) {
        val address = "http://${enticingConf.webserverConfiguration.fullAddress}/api/v1/query?settings=$settingsId"
        val (result, duration) = measureTimedValue {
            address.httpPost()
                    .jsonBody(SearchQuery(query))
                    .responseString().third.get().toDto<WebServer.ResultList>()
        }


        logger.info("Returned ${result.searchResults.size} snippets")

        result.searchResults.forEach { resultWriter.appendln(it.toString()) }
        resultWriter.flush()
        perfWriter.appendln("webserver,$query,${duration.inMilliseconds}")
        perfWriter.flush()
    }

    override fun close() {
        resultWriter.close()
        perfWriter.close()
    }
}