package cz.vutbr.fit.knot.enticing.index.boundary

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval

/**
 * Represents how the document was matched by the query according to the PostProcessor
 */
data class MatchInfo(
        /**
         * Intervals that were matched by the root of the AST
         */
        val rootIntervals: List<List<Interval>>,
        /**
         * Intervals that should be highlighted, that is leaves and identifiers
         */
        val leafIntervals: List<EqlMatch>
) {
    companion object {
        fun empty() = MatchInfo(emptyList(), emptyList())
    }
}

/**
 * One single match on the query
 */
sealed class EqlMatch {
    /**
     * Represents a match of a simple one token query ( leaf in the ast )
     */
    data class IndexMatch(
            /**
             * Interval over the query
             */
            val queryInterval: Interval,
            /**
             * Indexes in the document where the simple query was matched
             */
            val documentIndexes: List<Int>
    ) : EqlMatch()

    /**
     * Represents a match for some EQL identifier
     */
    data class IdentifierMatch(
            /**
             * Interval over the query
             */
            val queryInterval: Interval,
            /**
             * Intervals over the document where the identifier was matched
             */
            val documentIntervals: List<Interval>
    ) : EqlMatch()
}

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