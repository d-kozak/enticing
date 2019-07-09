package cz.vutbr.fit.knot.enticing.index.payload

import cz.vutbr.fit.knot.enticing.dto.query.ResponseFormat
import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.response.Payload
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetElement
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetPartsFields
import it.unimi.dsi.util.Interval

internal fun createPayload(query: SearchQuery, content: SnippetPartsFields, intervals: List<Interval>): Payload {

    // todo check for ResponseType once EQL stuff is in place
    val visitor = when (query.responseFormat) {
        ResponseFormat.HTML -> HtmlPayloadBuilderVisitor(content.corpusConfiguration, query, intervals)
        ResponseFormat.JSON -> JsonPayloadBuilderVisitor(content.corpusConfiguration, query, intervals)
    }

    for (elem in content) {
        if (visitor.isMatchStart(elem.index))
            visitor.visitMatchStart()
        when (elem) {
            is SnippetElement.Word -> visitor.visitWord(elem)
            is SnippetElement.Entity -> visitor.visitEntity(elem)
        }
        if (visitor.isMatchEnd(elem.index)) {
            visitor.visitMatchEnd()
        }
        if (elem != content.elements.last()) {
            visitor.visitSeparator()
        }
    }
    return visitor.getResult()
}


