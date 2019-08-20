package cz.vutbr.fit.knot.enticing.index.boundary

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.interval.Interval

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
    fun process(ast: AstNode, document: IndexedDocument, enclosingInterval: Interval? = null): Boolean

    /**
     * @param ast of the search query
     * @param document document to be processed
     * @param matchedIntervals information how the query was matched according to the [SearchEngine]
     * @return true if postprocessing was successful, false otherwise
     * when the method finishes, the ast is decorated with detailed match information
     */
    fun process(ast: AstNode, document: IndexedDocument, matchedIntervals: List<List<Interval>>): Boolean
}