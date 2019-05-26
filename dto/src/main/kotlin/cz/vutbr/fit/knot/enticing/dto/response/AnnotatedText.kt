package cz.vutbr.fit.knot.enticing.dto.response

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

data class AnnotatedText(
        @field:NotEmpty
        val text: String,
        val annotations: Map<Long, Annotation>,
        val positions: List<AnnotationPosition>,
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
        @field:Positive
        val from: Long,
        @field:Positive
        val to: Long,
        @field:NotEmpty
        val query: String
)

data class AnnotationPosition(
        @field:Positive
        val annotationId: Long,
        @field:Positive
        val from: Long,
        @field:Positive
        val to: Long
)