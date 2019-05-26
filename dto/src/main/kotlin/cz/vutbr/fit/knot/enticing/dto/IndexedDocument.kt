package cz.vutbr.fit.knot.enticing.dto

data class IndexedDocument(
        val id: Long,
        val title: String,
        val url: String,
        val body: AnnotatedText
)