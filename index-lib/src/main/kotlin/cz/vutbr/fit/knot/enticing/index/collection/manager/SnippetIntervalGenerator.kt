package cz.vutbr.fit.knot.enticing.index.collection.manager

import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.interval.Interval

@Cleanup("put into configuration?")
const val SNIPPET_SIZE = 50

/**
 * The algorithm works in the following way:
 *
 * 1) generate all regions that match the query
 * 2) sort them based on their length
 * 3) filter out longer ones that contain the shorter as subintervals
 * 4) filters out those longer than X units
 */
internal fun generateSnippetIntervals(result: List<List<Interval>>, documentSize: Int): List<Interval> {
    val allCombinations = computeAllIntervalCombinations(result)
    val sorted = allCombinations
            .asSequence()
            .filter { it.size < 1000 }
            .map {
                if (it.size < SNIPPET_SIZE) it.expand(SNIPPET_SIZE, 0, documentSize - 1) else it
            }
            .sortedBy { it.size }
            .toList()
    return sorted.filterIndexed { i, interval -> (0 until i).none { sorted[it].computeOverlap(interval) > 0.2 } }
}


internal fun computeAllIntervalCombinations(result: List<List<Interval>>): Set<Interval> {
    require(result.isNotEmpty()) { "Cannot compute snippets for empty result" }

    var list = result.first().map { Interval.valueOf(it.from, it.to) }.toSet()

    for ((i, indexResults) in result.withIndex()) {
        if (i == 0) continue
        val newList = mutableSetOf<Interval>()
        for (x in list) {
            for (y in indexResults) {
                val newInterval = Interval.valueOf(y.from, y.to)
                val mergedInterval = x.combineWith(newInterval)
                newList.add(mergedInterval)
            }
        }
        list = newList
    }
    return list
}
