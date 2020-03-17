package cz.vutbr.fit.knot.enticing.eql.compiler.matching

import cz.vutbr.fit.knot.enticing.dto.interval.Interval

data class DocumentMatch(
        val interval: Interval,
        val eqlMatch: List<EqlMatch>,
        val children: List<DocumentMatch>
)

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
        val match: Interval,
        val type: EqlMatchType = EqlMatchType.LEAF
)

enum class EqlMatchType {
    LEAF, ENTITY, IDENTIFIER
}