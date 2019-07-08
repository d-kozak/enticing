package cz.vutbr.fit.knot.enticing.index.query

import cz.vutbr.fit.knot.enticing.dto.query.ResponseFormat
import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.response.AnnotatedText
import cz.vutbr.fit.knot.enticing.dto.response.Payload
import cz.vutbr.fit.knot.enticing.dto.response.QueryMapping
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetElement
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetPartsFields

internal fun createPayload(query: SearchQuery, content: SnippetPartsFields, left: Int? = null, right: Int? = null): Payload {
    checkPreconditions(left, right, content, query)

    return when (query.responseFormat) {
        ResponseFormat.HTML -> produceHtml(content, query, left, right)
        ResponseFormat.JSON -> produceJson(content, query, left, right)
    }

}

private fun produceJson(content: SnippetPartsFields, query: SearchQuery, left: Int?, right: Int?): Payload {
    var startPosition = 0
    var endPosition = 0
    val text = buildString {
        var currentPosition = 0
        for (elem in content) {
            if (left != null && elem.index == left) {
                startPosition = currentPosition
            }

            when (elem) {
                is SnippetElement.Word -> {
                    val word = elem[content.corpusConfiguration.indexOf(query.defaultIndex)]
                    currentPosition += word.length
                    append(word)
                }
                is SnippetElement.Entity -> {
                    val phrase = elem[content.corpusConfiguration.indexOf(query.defaultIndex)].joinToString(" ")
                    currentPosition += phrase.length
                    append(phrase)
                }
            }

            if (right != null && elem.index == right - 1) {
                endPosition = currentPosition
            }

            if (elem != content.elements.last()) {
                append(' ')
                currentPosition += 1
            }
        }
    }
    val queryMapping = QueryMapping(textIndex = startPosition to endPosition, queryIndex = 0 to query.query.length)
    return Payload.Snippet.Json(AnnotatedText(text, emptyMap(), emptyList(), listOf(queryMapping)))
}

private fun produceHtml(content: SnippetPartsFields, query: SearchQuery, left: Int?, right: Int?): Payload.Snippet.Html {
    val result = buildString {
        for (elem in content) {
            if (left != null && elem.index == left) {
                append("<b>")
            }

            when (elem) {
                is SnippetElement.Word -> {
                    append(elem[content.corpusConfiguration.indexOf(query.defaultIndex)])
                }
                is SnippetElement.Entity -> {
                    append(elem[content.corpusConfiguration.indexOf(query.defaultIndex)].joinToString(" "))
                }
            }

            if (right != null && elem.index == right - 1) {
                append("</b>")
            }

            if (elem != content.elements.last()) {
                append(' ')
            }
        }
    }
    return Payload.Snippet.Html(result)
}

private fun checkPreconditions(left: Int?, right: Int?, content: SnippetPartsFields, query: SearchQuery) {
    if (left != null && right != null) {
        require(left < right) { "$left should be < to $right" }
    } else if (right == null || left == null) {
        throw IllegalArgumentException("Both or none of left and right should be null")
    }
    require(content[query.defaultIndex] != null) { "Data for default index are not present" }
}