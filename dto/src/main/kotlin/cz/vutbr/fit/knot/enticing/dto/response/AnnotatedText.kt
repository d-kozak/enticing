package cz.vutbr.fit.knot.enticing.dto.response

import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.PositiveOrZero

data class AnnotatedText(
        @field:NotEmpty
        val text: String,
        @field:Valid
        val annotations: Map<String, Annotation>,
        @field:Valid
        val positions: List<AnnotationPosition>,
        @field:Valid
        val queryMapping: List<QueryMapping>
)


data class Annotation(
        @field:NotBlank
        val id: String,
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
        @field:NotBlank
        val annotationId: String,
        @field:Valid
        val match: MatchedRegion,
        @field:Valid
        val subAnnotations: List<AnnotationPosition> = emptyList()
) {
    constructor(annotationId: String, match: Pair<Int, Int>) : this(annotationId, MatchedRegion(match.first, match.second))
}

data class MatchedRegion(
        @field:PositiveOrZero
        val from: Int,
        @field:PositiveOrZero
        val size: Int
)