package cz.vutbr.fit.knot.enticing.index.client

import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.TextFormat
import cz.vutbr.fit.knot.enticing.log.Logger
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.time.ExperimentalTime

/**
 * Result of running one test
 */
data class BenchmarkResult(
        /**
         * How many results were returned
         */
        val resultCount: Int,
        /**
         * Average time of one request
         */
        val averageTime: Double,
        /**
         * Deviation from the average time
         */
        val averageTimeDeviation: Double,
        /**
         * Minimal time
         */
        val minTime: Long,
        /**
         * Maximal time
         */
        val maxTime: Long
) {
    companion object {
        val CSV_HEADER = "query,results,avg,deviation,min,max"
    }

    fun toCsv(): String = buildString {
        append(resultCount)
        append(',')
        append(averageTime)
        append(',')
        append(averageTimeDeviation)
        append(',')
        append(minTime)
        append(',')
        append(maxTime)
    }
}

/**
 * Perform a benchmark specified by the params
 */
@ExperimentalTime
fun runBenchmark(query: String, target: QueryTarget, logger: Logger, collectAll: Boolean = false, iterations: Int = 100, snippetCount: Int = 1_000): BenchmarkResult {
    val searchQuery = SearchQuery(query, snippetCount = snippetCount, textFormat = TextFormat.PLAIN_TEXT)

    val durations = mutableListOf<Long>()
    val resultCnt = mutableMapOf<Int, Int>().withDefault { 0 }
    repeat(iterations - 1) {
        val (results, duration) = if (collectAll) target.collectAllResults(searchQuery) else target.submitQuery(searchQuery)
        resultCnt[results.size] = resultCnt.getValue(results.size) + 1
        durations.add(duration.toLongMilliseconds())
        logger.info("Round ${it + 1} finished, returned ${results.size} results in $duration")
    }

    if (resultCnt.size > 1) {
        logger.warn("different amount of results was returned")
        logger.warn(resultCnt.toString())
    }

    val average = durations.average()
    val deviation = sqrt((1.0 / iterations) * durations.map { (it - average).pow(2.0) }.sum())
    return BenchmarkResult(
            resultCount = resultCnt.keys.first(),
            averageTime = average,
            averageTimeDeviation = deviation,
            maxTime = durations.max()!!,
            minTime = durations.min()!!
    )
}