package cz.vutbr.fit.knot.enticing.index.payload

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.response.*
import cz.vutbr.fit.knot.enticing.dto.response.Annotation
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetElement
import it.unimi.dsi.util.Interval


class JsonPayloadBuilderVisitor(config: CorpusConfiguration, query: SearchQuery, intervals: List<Interval>
) : AbstractPayloadBuilderVisitor<Payload>(config, query, intervals) {

    private val annotations = mutableMapOf<String, Annotation>()
    private val positions = mutableListOf<AnnotationPosition>()
    private val queryMapping = mutableListOf<QueryMapping>()

    // todo @temporary this is just a dummy value until we know the real queryMatch (again, waiting for EQL)
    private val queryMatch = 0 to query.query.length

    private var startPosition = 0

    override fun visitMatchStart() {
        startPosition = builder.length
    }

    override fun visitWord(elem: SnippetElement.Word) {
        val word = elem[query.defaultIndex]

        val annotationContent = metaIndexes.map { it to elem[it] }.toMap()
        if (annotationContent.isNotEmpty())
            addAnnotation("w-${annotations.size}", annotationContent, builder.length, builder.length + word.length)
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

    private fun addAnnotation(id: String, content: Map<String, String>, from: Int, to: Int) {
        annotations[id] = Annotation(id, content)
        positions.add(AnnotationPosition(id, MatchedRegion(from, to)))
    }

    override fun getResult(): Payload = Payload.FullResponse.Annotated(
            AnnotatedText(builder.toString(), annotations, positions, queryMapping))
}

