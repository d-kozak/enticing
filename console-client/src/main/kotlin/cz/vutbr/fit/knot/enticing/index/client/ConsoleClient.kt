package cz.vutbr.fit.knot.enticing.index.client

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
            when (args.resultSize) {
                is ResultSize.All -> getAllResults(query)
                is ResultSize.Exact -> getLimitedResults(query, (args.resultSize as ResultSize.Exact).size)
            }

        }
    }

    private fun getAllResults(query: String) {

    }

    private fun getLimitedResults(query: String, size: Int) {

    }


    override fun close() {
        if (args.resultFile != null) resultStream.close()
    }
}

//
//
//val IndexServer.SearchResult.textUnitList: List<TextUnit>
//    get() = ((this.payload as ResultFormat.Snippet) as ResultFormat.Snippet.TextUnitList).content.content
//
//
//@ExperimentalTime
//class ConsoleClient(val args: ConsoleClientArgs, loggerFactory: LoggerFactory) : AutoCloseable {
//
//    private val enticingConf = args.configuration
//
//    private val corpusConf = args.corpusConfiguration
//
//    private val logger = loggerFactory.logger { }
//
//    var target = if (args.webserver) QueryTarget.WebserverTarget(enticingConf.webserverConfiguration.fullAddress, args.searchSettingsId)
//    else QueryTarget.QueryDispatcherTarget(corpusConf)
//
//
//    private var resultFormat = cz.vutbr.fit.knot.enticing.dto.ResultFormat.SNIPPET
//
//    private val resultWriter = FileWriter(args.resultFile, args.appendFiles)
//
//    private val perfWriter = FileWriter(args.perfFile, args.appendFiles)
//
//    var snippetCount = Defaults.snippetCount
//
//    var benchmarkIterations = 100
//
//    private val tokenIndex = corpusConf.metadataConfiguration.indexOf("token")
//
//    init {
//        logger.info("Loaded with args $args")
//        if (!args.appendFiles) {
//            resultWriter.appendln("some header :) todo")
//            perfWriter.appendln("targetComponent,query,millis")
//        }
//    }
//
//
//    fun run() {
//        when {
//            args.query.isNotEmpty() -> runQuery(args.query)
//            args.queryFile.isNotEmpty() -> runFromFile(args.queryFile)
//            args.benchmark.isNotEmpty() -> runBenchmark(File(args.benchmark))
//            args.shell -> runShell()
//            else -> {
//                logger.warn("No option specified, starting the shell as a default")
//                runShell()
//            }
//        }
//    }
//
//    private fun runFromFile(queryFile: String) {
//        for (line in File(queryFile).readLines())
//            if (!line.startsWith("#"))
//                runQuery(line)
//
//    }
//
//    private fun runShell() {
//        logger.info("Starting interactive shell")
//        lineSequence()
//                .withCommandHandler()
//                .forEach { line -> runQuery(line) }
//    }
//
//
//    private fun Sequence<String>.withCommandHandler(): Sequence<String> = sequence {
//        loop@ for (line in this@withCommandHandler) {
//            when {
//                line.startsWith("#") -> continue@loop
//                line.startsWith("$") -> handleCommand(line.substring(1))
//                else -> yield(line)
//            }
//        }
//    }
//
//    private fun handleCommand(cmd: String) {
//        val parts = cmd.split(" ")
//        if (parts.isEmpty()) {
//            logger.warn("Cannot parse command $cmd")
//        }
//        when (parts[0]) {
//            "webserver", "w" -> {
//                logger.info("Using webserver")
//
//                if (parts.size >= 2) {
//                    val address = parts[1]
//                    var port = if (parts.size >= 3) parts[2].toIntOrNull() else enticingConf.webserverConfiguration.port
//                    if (port == null) {
//                        logger.warn("${parts[2]} is not a valid port number")
//                        port = enticingConf.webserverConfiguration.port
//                    }
//                    enticingConf.webserverConfiguration.address = address
//                    enticingConf.webserverConfiguration.port = port
//
//                }
//                logger.info("setting webserver conf to ${enticingConf.webserverConfiguration}")
//                target = QueryTarget.WebserverTarget(enticingConf.webserverConfiguration.fullAddress, args.searchSettingsId)
//            }
//            "dispatch", "d" -> {
//                logger.info("Using query dispatcher")
//                target = QueryTarget.QueryDispatcherTarget(corpusConf)
//            }
//            "index", "i" -> {
//                if (parts.size != 2) {
//                    logger.warn("Index server address expected")
//                    return
//                }
//                val address = parts[1]
//                logger.info("Using index server $address")
//                target = QueryTarget.IndexServerTarget(address)
//            }
//            "format", "f" -> {
//                if (parts.size != 2) {
//                    logger.warn("Result format expected (idlist vs snippet)")
//                    return
//                }
//                when (parts[1]) {
//                    "snippet" -> this.resultFormat = cz.vutbr.fit.knot.enticing.dto.ResultFormat.SNIPPET
//                    "idlist" -> this.resultFormat = cz.vutbr.fit.knot.enticing.dto.ResultFormat.IDENTIFIER_LIST
//                    else -> logger.warn("Unknown format ${parts[1]}")
//                }
//                logger.info("new format ${this.resultFormat}")
//            }
//            "benchmark", "b" -> {
//                if (parts.size != 2) {
//                    logger.warn("Query file expected")
//                    return
//                }
//                runBenchmark(File(parts[1]))
//            }
//            "snippetCount" -> {
//                if (parts.size < 2) {
//                    logger.warn("limit expected")
//                    return
//                }
//                val num = parts[1].toIntOrNull()
//                if (num == null) {
//                    logger.warn("${parts[1]} is not a number")
//                    return
//                }
//                snippetCount = num
//                logger.info("Set snippet count to $num")
//            }
//            "benchmarkIterations" -> {
//                if (parts.size < 2) {
//                    logger.warn("one number expected")
//                    return
//                }
//                val num = parts[1].toIntOrNull()
//                if (num == null) {
//                    logger.warn("${parts[1]} is not a number")
//                    return
//                }
//                benchmarkIterations = num
//                logger.info("Set benchmark iterations to $benchmarkIterations")
//            }
//            else -> logger.warn("Unknown command $cmd")
//        }
//    }
//
//    private fun runBenchmark(input: File) {
//        logger.info("Running benchmark from file ${input.absolutePath}")
//        if (!input.isFile || !input.canRead()) {
//            logger.warn("Invalid file ${input.absolutePath}")
//            return
//        }
//        val queries = input.readLines()
//                .asSequence()
//                .withCommandHandler()
//
//        val results = runBenchmark(queries, this, logger)
//        val resultFile = "${input.absolutePath}.out.csv"
//        FileWriter(resultFile).use {
//            it.appendln(BenchmarkResult.CSV_HEADER)
//            for (result in results) {
//                it.appendln(result.toCsv())
//            }
//        }
//    }
//
//    private fun runQuery(query: String) {
//        logger.info("Running query '$query'")
//        try {
//            val (results, duration) = target.query(SearchQuery(query, snippetCount = snippetCount, resultFormat = resultFormat, uuid = UUID.randomUUID()))
//            processResults(query, results, duration, target.name)
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//    }
//
//
//    private fun processResults(query: String, results: List<IndexServer.SearchResult>, duration: Duration, component: String) {
//        resultWriter.appendln("Running query '$query' on $component")
//        results.forEach {
//            val text = when (it.payload) {
//                is ResultFormat.Snippet -> it.textUnitList.toRawText(tokenIndex)
//                is ResultFormat.IdentifierList -> (it.payload as ResultFormat.IdentifierList).list.joinToString("\n") {
//                    it.identifier + " := " + (it.snippet as ResultFormat.Snippet.TextUnitList).content.content.toRawText(tokenIndex)
//                }
//            }
//            resultWriter.appendln(text)
//        }
//        resultWriter.flush()
//        resultWriter.appendln("Query finished, it took ${duration.inMilliseconds} ms")
//        perfWriter.appendln("$component,$query,${duration.inMilliseconds}")
//        perfWriter.flush()
//
//        logger.info("Returned ${results.size} snippets, took ${duration.inMilliseconds} ms")
//    }
//
//
//    override fun close() {
//        resultWriter.close()
//        perfWriter.close()
//    }
//}