package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.util

import cz.vutbr.fit.knot.enticing.dto.interval.Interval

/**
 * Segment tree for storing information which intervals are already taken and which are not.
 * Should handle updates and queries in O(log n) time while taking O(n) memory.
 *
 * This class represents a single node in the tree. Updates and queries should be done from the root node.
 */
class SegmentTree(
        /**
         * The leftmost point that should be covered by this node
         */
        var leftmost: Int,
        /**
         * The rightmost point that should be covered by this node
         */
        var rightmost: Int,
        /**
         * Is the whole interval covered by this node still free?
         * This information is computed from the child nodes.
         *
         * If there is AT LEAST ONE false in node's subtree, this value is false.
         */
        private var wholeIntervalFree: Boolean = true,
        /**
         * Was the whole interval set by a range query? This is basically an information that should
         * be lazy propagated down to turn all descendants to false, but it is not necessary, because it can
         * be just checked at this node's level and if there is any overlap, return false immediately.
         * Essentially true here means that ALL VALUES BELOW are false.
         */
        private var wholeIntervalTaken: Boolean = false,
        /**
         * Left child, if any
         */
        private var leftChild: SegmentTree? = null,
        /**
         * Right child, if any
         */
        private var rightChild: SegmentTree? = null
) {

    init {
        require(leftmost <= rightmost) { "Empty interval detected [$leftmost,$rightmost]" }
        if (!isLeaf()) {
            val mid = leftmost + (rightmost - leftmost) / 2
            leftChild = SegmentTree(leftmost, mid)
            rightChild = SegmentTree(mid + 1, rightmost)
        }
    }

    /**
     * @params left,right - queried interval
     * @return true iff the whole interval is free
     */
    fun isFree(left: Int, right: Int): Boolean {
        require(left <= right) { "Empty query interval detected" }
        return when {
            isLeaf() -> wholeIntervalFree
            noOverlap(left, right) -> true
            wholeIntervalTaken -> false
            fullyCovered(left, right) -> wholeIntervalFree
            else -> leftChild!!.isFree(left, right) && rightChild!!.isFree(left, right)
        }
    }

    /**
     * sets all nodes in given interval as taken
     * @params left,right - queried interval
     */
    fun take(left: Int, right: Int) {
        require(left <= right) { "Empty query interval detected" }
        when {
            isLeaf() -> wholeIntervalFree = false
            noOverlap(left, right) -> Unit // nothing to do
            fullyCovered(left, right) -> {
                wholeIntervalFree = false
                wholeIntervalTaken = true
            }
            else -> {
                leftChild!!.take(left, right)
                rightChild!!.take(left, right)
                wholeIntervalFree = leftChild!!.wholeIntervalFree && rightChild!!.wholeIntervalFree
            }
        }
    }

    /**
     * @return true iff the query fully covers the node (node is only a part of the query)
     */
    private fun fullyCovered(left: Int, right: Int) = left <= leftmost && rightmost <= right

    /**
     * @return true iff queried interval has no overlap with this node
     */
    private fun noOverlap(left: Int, right: Int) = left > rightmost || right < leftmost

    /**
     * @return true iff this node is a leaf node
     */
    private fun isLeaf() = leftmost == rightmost
}

fun SegmentTree.isFree(interval: Interval) = this.isFree(interval.from, interval.to)
fun SegmentTree.take(interval: Interval) = this.take(interval.from, interval.to)