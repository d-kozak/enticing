package cz.vutbr.fit.knot.enticing.dto


data class SearchResult(
        val docId: Long,
        val location: Long,
        val size: Long,
        val snippet: AnnotatedText,
        val url: String,
        val canExtend: Boolean
)


