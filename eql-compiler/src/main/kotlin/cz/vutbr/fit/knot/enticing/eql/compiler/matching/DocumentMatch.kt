package cz.vutbr.fit.knot.enticing.eql.compiler.matching

import cz.vutbr.fit.knot.enticing.dto.interval.Interval

/**
 * Describes how the document matched the query
 */
data class DocumentMatch(
        val interval: Interval,
        val eqlMatch: List<EqlMatch>,
        val children: List<DocumentMatch>
)

/**
 * One simple part of the match
 */
data class EqlMatch(
        /**
         * Interval over the query
         */
        val queryInterval: Interval,
        /**
         * Indexes in the document where the simple query was matched
         */
        val match: Interval,
        val type: EqlMatchType = EqlMatchType.Leaf
)

/**
 * Type of a simple match - can be
 * 1) single word
 * 2) entity
 * 3) identifier
 */
sealed class EqlMatchType {
    object Leaf : EqlMatchType()
    object Entity : EqlMatchType()
    data class Identifier(val name: String) : EqlMatchType()
}