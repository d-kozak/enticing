package cz.vutbr.fit.knot.enticing.index.payload

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.Annotation
import cz.vutbr.fit.knot.enticing.dto.annotation.Temporary
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetElement
import it.unimi.dsi.util.Interval


class JsonPayloadBuilderVisitor(config: CorpusConfiguration, query: Mg4jQuery, intervals: List<Interval>
) : AbstractPayloadBuilderVisitor<Payload>(config, query, intervals) {

    private val annotations = mutableMapOf<String, Annotation>()
    private val positions = mutableListOf<AnnotationPosition>()
    private val queryMapping = mutableListOf<QueryMapping>()

    @Temporary("this is just a dummy value until we know the real queryMatch (waiting for EQL)")
    private val queryMatch = 0 to 1

    private var startPosition = 0

    override fun visitMatchStart() {
        startPosition = builder.length
    }

    override fun visitWord(elem: SnippetElement.Word) {
        val word = elem[query.defaultIndex]

        val annotationContent = metaIndexes.map { it to elem[it] }.toMap()
        if (annotationContent.isNotEmpty())
            addAnnotation("w-${annotations.size}", annotationContent, builder.length, word.length)
        builder.append(word)
    }

    override fun visitEntity(entity: SnippetElement.Entity) {
        val phrase = entity[query.defaultIndex].joinToString(" ")
        builder.append(phrase)
    }

    override fun visitMatchEnd() {
        queryMapping.add(QueryMapping(startPosition to builder.length, queryMatch))
    }

    override fun visitSeparator() {
        builder.append(' ')
    }

    private fun addAnnotation(id: String, content: Map<String, String>, from: Int, size: Int) {
        annotations[id] = Annotation(id, content)
        positions.add(AnnotationPosition(id, MatchedRegion(from, size)))
    }

    override fun getResult(): Payload {
        @Temporary("The result should actually never be empty, this is here only until the query processing is not finalized")
        val text = if (builder.isNotEmpty()) builder.toString() else "!!!EMPTY!!!"
        return Payload.FullResponse.Annotated(
                AnnotatedText(text, annotations, positions, queryMapping))
    }
}

