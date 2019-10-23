package cz.vutbr.fit.knot.enticing.index.boundary

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval

/**
 * Represents how the document was matched by the query according to the PostProcessor
 */
data class MatchInfo(
        /**
         * Consists of root intervals and their subintervals that should be highlighted (indexes and identifiers)
         */
        val intervals: List<Pair<Interval, List<EqlMatch>>>
) {
    companion object {
        fun empty() = MatchInfo(emptyList())
    }
}

/**
 * One single match on the query
 */
data class EqlMatch(
        /**
         * Interval over the query
         */
        val queryInterval: Interval,
        /**
         * Indexes in the document where the simple query was matched
         */
        val match: Interval
)

/**
 * Execute postprocessing
 */
interface PostProcessor {
    /**
     * @param ast of the search query
     * @param document document to be processed
     * @param enclosingInterval interval that should be analyzed
     * @return true if postprocessing was successful, false otherwise
     * when the method finishes, the ast is decorated with detailed match information
     */
    fun process(ast: AstNode, document: IndexedDocument, defaultIndex: String, corpusConfiguration: CorpusConfiguration, enclosingInterval: Interval? = null): MatchInfo?

    /**
     * @param ast of the search query
     * @param document document to be processed
     * @param matchedIntervals information how the query was matched according to the [SearchEngine]
     * @return true if postprocessing was successful, false otherwise
     * when the method finishes, the ast is decorated with detailed match information
     */
    fun process(ast: AstNode, document: IndexedDocument, defaultIndex: String, corpusConfiguration: CorpusConfiguration, matchedIntervals: List<List<Interval>>): MatchInfo?
}