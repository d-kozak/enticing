package cz.vutbr.fit.knot.enticing.webserver.dto

import com.fasterxml.jackson.databind.ObjectMapper


data class SearchResult(
        val docId: Long,
        val location: Long,
        val size: Long,
        val snippet: AnnotatedText,
        val url: String,
        val canExtend: Boolean
)


data class IndexedDocument(
        val id: Long,
        val title: String,
        val url: String,
        val body: AnnotatedText
)

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

fun <T> T.toJson(): String = ObjectMapper().writeValueAsString(this)
