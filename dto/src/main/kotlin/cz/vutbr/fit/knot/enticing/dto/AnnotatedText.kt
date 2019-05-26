package cz.vutbr.fit.knot.enticing.dto

data class AnnotatedText(
        val text: String,
        val annotations: Map<Long, Annotation>,
        val positions: List<AnnotationPosition>,
        val queryMapping: List<QueryMapping>
)

data class Annotation(
        val id: Long,
        val type: String,
        val image: String?,
        val content: Map<String, String>
)

data class QueryMapping(
        val from: Long,
        val to: Long,
        val query: String
)

data class AnnotationPosition(
        val annotationId: Long,
        val from: Long,
        val to: Long
)