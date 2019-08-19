package cz.vutbr.fit.knot.enticing.index.collection.manager

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.interval.Interval


internal fun generateSnippetIntervals(result: List<List<Interval>>, documentSize: Int): List<Pair<Interval, List<Interval>>> {
    val allCombinations = computeAllIntervalCombinations(result)
    val sorted = allCombinations
            .asSequence()
            .filter { it.first.size < 1000 }
            .map {
                if (it.first.size < SNIPPET_SIZE) it.first.expand(SNIPPET_SIZE, 0, documentSize - 1) to it.second else it
            }
            .sortedBy { it.first.size }
            .toList()


    @Incomplete("filter out the ones that are too overlapping")
    return sorted
}

/**
 * The algorithm works in the following way:
 *
 * 1) generate all regions that match the query
 * 2) sort them based on their length
 * 3) filter out longer ones that contain the shorter as subintervals
 * 4) filters out those longer than X units
 */
internal fun computeAllIntervalCombinations(result: List<List<Interval>>): List<Pair<Interval, List<Interval>>> {
    require(result.isNotEmpty()) { "Cannot compute snippets for empty result" }

    var list = result.first().map { Interval.valueOf(it.from, it.to) to listOf(Interval.valueOf(it.from, it.to)) }

    for ((i, indexResults) in result.withIndex()) {
        if (i == 0) continue
        val newList = mutableListOf<Pair<Interval, List<Interval>>>()
        for (x in list) {
            for (y in indexResults) {
                val newInterval = Interval.valueOf(y.from, y.to)
                val mergedInderval = x.first.combineWith(newInterval)
                val extendedList = x.second.toMutableList().also {
                    it.add(newInterval)
                }
                newList.add(mergedInderval to extendedList)
            }
        }
        list = newList
    }
    return list
}
