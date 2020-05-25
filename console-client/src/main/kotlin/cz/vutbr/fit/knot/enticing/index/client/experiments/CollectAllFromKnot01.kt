package cz.vutbr.fit.knot.enticing.index.client.experiments

import cz.vutbr.fit.knot.enticing.index.client.BenchmarkResult
import cz.vutbr.fit.knot.enticing.index.client.QueryTarget
import cz.vutbr.fit.knot.enticing.index.client.runBenchmark
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import java.io.File
import java.io.FileWriter
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun main() = FileWriter("./console-client/src/test/resources/knot01.benchmark7.csv").use { writer ->
    writer.appendln(BenchmarkResult.CSV_HEADER)
    val logger = SimpleStdoutLoggerFactory.namedLogger("CollectAllFromKnot01")

    val target = QueryTarget.IndexServerTarget("knot01.fit.vutbr.cz:5627", SimpleStdoutLoggerFactory)

    val queries = File("./console-client/src/test/resources/benchmark.eql").readLines()
            .filterNot { it.isBlank() || it.startsWith('#') || it.startsWith('$') }

    val results = mutableMapOf<String, BenchmarkResult>()

    for (query in queries) {
        logger.info("Executing query $query")
        val res = runBenchmark(query, target, logger, iterations = 100, snippetCount = 20)
        writer.append(query).append(',')
        writer.appendln(res.toCsv())
        logger.info(res.toString())
        results[query] = res
    }

}