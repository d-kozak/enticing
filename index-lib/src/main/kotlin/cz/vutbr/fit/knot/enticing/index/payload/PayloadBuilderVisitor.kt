package cz.vutbr.fit.knot.enticing.index.payload

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetElement
import cz.vutbr.fit.knot.enticing.index.query.component1
import cz.vutbr.fit.knot.enticing.index.query.component2
import it.unimi.dsi.util.Interval

interface PayloadBuilderVisitor<T> {

    fun visitMatchStart()

    fun visitWord(word: SnippetElement.Word)

    fun visitEntity(entity: SnippetElement.Entity)

    fun visitMatchEnd()

    fun visitSeparator()

    fun getResult(): T
}

abstract class AbstractPayloadBuilderVisitor<T>(
        protected val config: CorpusConfiguration,
        protected val query: SearchQuery,
        protected val intervals: List<Interval>)
    : PayloadBuilderVisitor<T> {
    protected val builder: StringBuilder = StringBuilder()

    protected val defaultIndex: String = query.defaultIndex
    protected val metaIndexes = config.getMetaIndexes(defaultIndex)

    private val left: Set<Int>
    private val right: Set<Int>

    init {
        val split = split(intervals)
        left = split.first
        right = split.second
    }

    fun isMatchStart(index: Int) = index in left

    fun isMatchEnd(index: Int) = index in right
}


private fun split(intervals: List<Interval>): Pair<Set<Int>, Set<Int>> {
    val left = mutableSetOf<Int>()
    val right = mutableSetOf<Int>()
    for ((l, r) in intervals) {
        left.add(l)
        right.add(r)
    }
    return Pair(left, right)
}
