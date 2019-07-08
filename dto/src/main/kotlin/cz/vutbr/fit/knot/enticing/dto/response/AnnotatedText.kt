package cz.vutbr.fit.knot.enticing.dto.response

import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

data class AnnotatedText(
        @field:NotEmpty
        val text: String,
        @field:Valid
        val annotations: Map<Long, Annotation>,
        @field:Valid
        val positions: List<AnnotationPosition>,
        @field:Valid
        val queryMapping: List<QueryMapping>
)

data class Annotation(
        @field:Positive
        val id: Long,
        @field:NotEmpty
        val type: String,
        val image: String?,
        val content: Map<String, String>
)

data class QueryMapping(
        @field:Valid
        val textIndex: MatchedRegion,
        @field:Valid
        val queryIndex: MatchedRegion
) {
        constructor(textIndex: Pair<Int, Int>, queryIndex: Pair<Int, Int>) : this(MatchedRegion(textIndex.first, textIndex.second), MatchedRegion(queryIndex.first, queryIndex.second))
}

data class AnnotationPosition(
        @field:Positive
        val annotationId: Long,
        @field:Valid
        val match: MatchedRegion
)

data class MatchedRegion(
        @field:PositiveOrZero
        val from: Int,
        @field:PositiveOrZero
        val size: Int
)