package cz.vutbr.fit.knot.enticing.index.query

import cz.vutbr.fit.knot.enticing.dto.query.ResponseFormat
import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.response.AnnotatedText
import cz.vutbr.fit.knot.enticing.dto.response.Payload
import cz.vutbr.fit.knot.enticing.dto.response.QueryMapping
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetElement
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetPartsFields
import it.unimi.dsi.util.Interval

internal fun createPayload(query: SearchQuery, content: SnippetPartsFields, intervals: List<Interval>): Payload {

    // todo check for ResponseType once EQL stuff is in place
    return when (query.responseFormat) {
        ResponseFormat.HTML -> produceHtml(content, query, intervals)
        ResponseFormat.JSON -> produceJson(content, query, intervals)
    }
}

private fun produceJson(content: SnippetPartsFields, query: SearchQuery, intervals: List<Interval>): Payload {
    val (left, right) = split(intervals)

    val queryIndex = 0 to query.query.length
    val queryMapping = mutableListOf<QueryMapping>()
    val text = buildString {
        var startPosition = 0
        var currentPosition = 0
        for (elem in content) {
            if (elem.index in left) {
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

            if (elem.index in right) {
                queryMapping.add(QueryMapping(startPosition to currentPosition, queryIndex))
            }

            if (elem != content.elements.last()) {
                append(' ')
                currentPosition += 1
            }
        }
    }
    return Payload.Snippet.Json(AnnotatedText(text, emptyMap(), emptyList(), queryMapping))
}

private fun produceHtml(content: SnippetPartsFields, query: SearchQuery, intervals: List<Interval>): Payload.Snippet.Html {
    // todo extend according to the html snippet format spec (when there is one :) )
    val (left, right) = split(intervals)

    val result = buildString {
        for (elem in content) {
            if (elem.index in left) {
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

            if (elem.index in right) {
                append("</b>")
            }

            if (elem != content.elements.last()) {
                append(' ')
            }
        }
    }
    return Payload.Snippet.Html(result)
}

private fun split(intervals: List<Interval>): Pair<Set<Int>, Set<Int>> {
    val left = mutableSetOf<Int>()
    val right = mutableSetOf<Int>()
    for ((l, r) in intervals) {
        left.add(l)
        right.add(r)
    }
    return Pair(left, right)
}
