package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text

import cz.vutbr.fit.knot.enticing.dto.Mg4jQuery
import cz.vutbr.fit.knot.enticing.dto.TextFormat
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.DocumentElement
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.StructuredDocumentContent

internal fun createPayload(query: Mg4jQuery, content: StructuredDocumentContent, intervals: List<Interval>, corpusConfiguration: CorpusConfiguration): ResultFormat {

    @Incomplete("check for ResponseType once EQL stuff is in place")
    val visitor = when (query.textFormat) {
        TextFormat.HTML -> HtmlPayloadBuilderVisitor(content.corpusConfiguration, query, intervals)
        TextFormat.STRING_WITH_METADATA -> JsonPayloadBuilderVisitor(content.corpusConfiguration, query, intervals)
        else -> return ResultFormat.Snippet.TextUnitList(createNewAnnotatedText(content, intervals, corpusConfiguration))
    }

    for (elem in content) {
        if (visitor.isMatchStart(elem.index))
            visitor.visitMatchStart()
        when (elem) {
            is DocumentElement.Word -> visitor.visitWord(elem)
            is DocumentElement.Entity -> visitor.visitEntity(elem)
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


