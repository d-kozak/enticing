package cz.vutbr.fit.knot.enticing.index.boundary

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.eql.compiler.matching.DocumentMatch

/**
 * Represents how the document was matched by the query according to the PostProcessor
 */
data class MatchInfo(
        /**
         * Consists of root intervals and their subintervals that should be highlighted (indexes and identifiers)
         */
        val intervals: List<DocumentMatch>
) : Iterable<DocumentMatch> {
    companion object {
        fun empty() = MatchInfo(emptyList())
    }

    override fun iterator(): Iterator<DocumentMatch> = intervals.iterator()
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
    fun process(ast: AstNode, document: IndexedDocument, defaultIndex: String, metadataConfiguration: MetadataConfiguration, enclosingInterval: Interval? = null): MatchInfo?

    /**
     * @param ast of the search query
     * @param document document to be processed
     * @param matchedIntervals information how the query was matched according to the [SearchEngine]
     * @return true if postprocessing was successful, false otherwise
     * when the method finishes, the ast is decorated with detailed match information
     */
    fun process(ast: AstNode, document: IndexedDocument, defaultIndex: String, metadataConfiguration: MetadataConfiguration, matchedIntervals: List<List<Interval>>): MatchInfo?
}