package cz.vutbr.fit.knot.enticing.index.query

import cz.vutbr.fit.knot.enticing.dto.Interval
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import it.unimi.di.big.mg4j.query.SelectedInterval


internal fun generateSnippetIntervals(result: Collection<Array<SelectedInterval>>, documentSize: Int): List<Interval> {
    val allCombinations = computeAllIntervalCombinations(result)
    val sorted = allCombinations
            .asSequence()
            .filter { it.size < 200 }
            .map {
                if (it.size < SNIPPET_SIZE) it.expand(SNIPPET_SIZE, 0, documentSize - 1) else it
            }
            .sortedBy { it.size }
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
internal fun computeAllIntervalCombinations(result: Collection<Array<SelectedInterval>>): List<Interval> {
    require(result.isNotEmpty()) { "Cannot compute snippets for empty result" }

    var list = listOf(Interval.empty())

    for (indexResults in result) {
        val newList = mutableListOf<Interval>()
        for (x in list) {
            for (y in indexResults) {
                newList.add(x.combineWith(Interval.valueOf(y.interval.left, y.interval.right)))
            }
        }
        list = newList
    }
    return list
}
