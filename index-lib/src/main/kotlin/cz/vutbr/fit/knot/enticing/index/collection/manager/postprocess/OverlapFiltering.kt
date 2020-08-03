package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.eql.compiler.matching.DocumentMatch
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.boundary.MatchInfo
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.util.SegmentTree
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.util.isFree
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.util.take

/**
 * The goal of this postprocessing is filtering out all overlapping intervals.
 *
 * The input is a list of intervals.
 *
 * The output is a filtered version of the input such that no intervals are overlapping and the shorter intervals are preferred over longer ones.
 *
 * Steps:
 * 1) sort the intervals based on their length, shortest first
 *      * intervals with same length are sorted based on their starting position
 * 2) traverse the sorted list and for each interval:
 *      2a) check if it is not overlapping with anything already taken
 *          * if not, take it and mark appropriate part of the document as taken
 *
 * Now the question is how to store and query the information about which intervals of the document are already taken?
 *
 * Approach one - segment tree, which stores for each index whether it is used or not.
 * The problem is that we need both interval query and interval update, therefore we would need a segment tree with lazy propagation.
 * But since all we need is a binary information and we will only change it once, we could just keep it in the middle nodes, no need to actually propagate anything down or up :)
 */

/**
 * First by size, then by starting position
 */
private val matchComparator = compareBy<DocumentMatch> { it.interval.size }
        .thenBy { it.interval.from }

fun filterOverlappingIntervals(document: IndexedDocument, matchInfo: MatchInfo): MatchInfo {
    val matchList = matchInfo.intervals.sortedWith(matchComparator)
    val root = SegmentTree(0, document.size - 1)
    val res = mutableListOf<DocumentMatch>()

    for (match in matchList) {
        if (root.isFree(match.interval)) {
            root.take(match.interval)
            res.add(match)
        }
    }

    return MatchInfo(res)
}
