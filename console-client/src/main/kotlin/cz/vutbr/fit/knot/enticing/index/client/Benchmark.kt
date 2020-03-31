//package cz.vutbr.fit.knot.enticing.index.client
//
//import cz.vutbr.fit.knot.enticing.dto.SearchQuery
//import cz.vutbr.fit.knot.enticing.log.Logger
//import kotlin.math.abs
//import kotlin.math.pow
//import kotlin.time.ExperimentalTime
//
//
//data class BenchmarkResult(
//        val query: String,
//        val results: Int,
//        val min: Long,
//        val max: Long,
//        val avg: Double,
//        val deviation: Double,
//        val durations: List<Long>
//) {
//    companion object {
//        val CSV_HEADER = "query,results,avg,deviation,min,max"
//    }
//
//    fun toCsv(): String = "$query,$results,$avg,$deviation,$min,$max"
//}
//
//@ExperimentalTime
//fun runBenchmark(queries: Sequence<String>, consoleClient: ConsoleClient, logger: Logger): List<BenchmarkResult> {
//    logger.info("Benchmark started, ${consoleClient.benchmarkIterations} iterations")
//    val results = mutableListOf<BenchmarkResult>()
//
//    for ((i, query) in queries.withIndex()) {
//        logger.info("Running query ${i + 1}: '$query'")
//        val durations = mutableListOf<Long>()
//        var resultCount = -1
//        var j = 0
//        while (j++ < consoleClient.benchmarkIterations) {
//            print(".")
//            try {
//                val (searchResults, duration) = consoleClient.target.query(SearchQuery(query, snippetCount = consoleClient.snippetCount))
//                if (resultCount == -1) resultCount = searchResults.size
//                else if (resultCount != searchResults.size) {
//                    logger.error("Query '$query' returned different number of results this time: $resultCount vs ${searchResults.size}")
//                }
//                durations.add(duration.toLongMilliseconds())
//            } catch (ex: Exception) {
//                logger.warn("Query '$query', iteration $j failed: ${ex.message}")
//            }
//        }
//        println()
//        if (durations.isEmpty()) {
//            logger.warn("No successful run for query '$query', cannot provide results")
//            continue
//        }
//        val min = durations.min()!!
//        val max = durations.max()!!
//        val avg = durations.average()
//        val deviation = durations.map { abs(it.toDouble() - avg) }
//                .map { it.pow(2) }
//                .sum() / durations.size
//        val result = BenchmarkResult(query, resultCount, min, max, avg, deviation, durations)
//        logger.info("query ${i + 1}: '$query' \n\t $result")
//        results.add(result)
//    }
//
//    return results
//}
//
//
