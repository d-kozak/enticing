package cz.vutbr.fit.knot.enticing.index.payload

import cz.vutbr.fit.knot.enticing.dto.Interval
import cz.vutbr.fit.knot.enticing.dto.Mg4jQuery
import cz.vutbr.fit.knot.enticing.dto.Payload
import cz.vutbr.fit.knot.enticing.dto.ResponseFormat
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetElement
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetPartsFields

internal fun createPayload(query: Mg4jQuery, content: SnippetPartsFields, intervals: List<Interval>, corpusConfiguration: CorpusConfiguration): Payload {

    @Incomplete("check for ResponseType once EQL stuff is in place")
    val visitor = when (query.responseFormat) {
        ResponseFormat.HTML -> HtmlPayloadBuilderVisitor(content.corpusConfiguration, query, intervals)
        ResponseFormat.ANNOTATED_TEXT -> JsonPayloadBuilderVisitor(content.corpusConfiguration, query, intervals)
        else -> return Payload.FullResponse.NewAnnotated(createNewAnnotatedText(content, intervals, corpusConfiguration))
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


