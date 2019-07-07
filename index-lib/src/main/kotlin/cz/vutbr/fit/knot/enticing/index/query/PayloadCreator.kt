package cz.vutbr.fit.knot.enticing.index.query

import cz.vutbr.fit.knot.enticing.dto.query.ResponseFormat
import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.response.Payload
import cz.vutbr.fit.knot.enticing.index.mg4j.DocumentContent


fun createPayload(query: SearchQuery, content: DocumentContent, left: Int, right: Int): Payload {
    checkPreconditions(left, right, content, query)

    return when (query.responseFormat) {
        ResponseFormat.HTML -> produceHtml(content, query, left, right)
        else -> TODO("unfinished")
    }

}

private fun produceHtml(content: DocumentContent, query: SearchQuery, left: Int, right: Int): Payload.Snippet.Html {
    val defaultIndex = content[query.defaultIndex]!!
    val result = buildString {
        for ((i, word) in defaultIndex.withIndex()) {
            if (i == left) {
                append("<b>")
            }

            append(word)
            if (i != defaultIndex.size - 1 && i != right - 1) {
                append(' ')
            }

            if (i == right - 1) {
                append("</b>")
            }
        }
    }
    return Payload.Snippet.Html(result)
}

private fun checkPreconditions(left: Int, right: Int, content: DocumentContent, query: SearchQuery) {
    require(left < right) { "$left should be < to $right" }
    for ((index, value) in content) {
        require(left < value.size) { "Left $left is too big, content size at index $index is ${value.size}" }
        require(right <= value.size) { "Right $right is too big, content size index $index is ${value.size}" }
    }
    require(content[query.defaultIndex] != null) { "Data for default index are not present" }
}