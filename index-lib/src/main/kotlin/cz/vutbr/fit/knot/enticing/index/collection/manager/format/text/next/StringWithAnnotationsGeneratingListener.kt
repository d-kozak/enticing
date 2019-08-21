package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.next

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.text.*
import cz.vutbr.fit.knot.enticing.dto.format.text.Annotation
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import org.slf4j.LoggerFactory

class StringWithAnnotationsGeneratingListener(config: CorpusConfiguration, defaultIndexName: String) : TextFormatGeneratingListener(config, defaultIndexName) {

    private val log = LoggerFactory.getLogger(StringWithAnnotationsGeneratingListener::class.java)

    private val builder = StringBuilder()
    private val annotations = mutableMapOf<String, Annotation>()
    private val positions = mutableListOf<AnnotationPosition>()
    private val queryMapping = mutableListOf<QueryMapping>()


    private var startPosition = -1
    private var queryInterval: Interval? = null

    override fun matchStart(queryInterval: Interval) {
        this.startPosition = builder.length
        this.queryInterval = queryInterval
    }

    override fun matchEnd() {
        if (startPosition != -1 && queryInterval != null) {
            queryMapping.add(QueryMapping(startPosition to builder.length, queryInterval!!.from to queryInterval!!.to))
            startPosition = -1
            queryInterval = null
        } else {
            log.error("matchEnd executed, but there was no start position")
        }
    }


    private var attributes: List<String>? = null
    private var entityClass: String? = null

    override fun entityStart(attributes: List<String>, entityClass: String) {
        this.attributes = attributes
        this.entityClass = entityClass
    }

    override fun entityEnd() {
        if (attributes != null && entityClass != null) {
            val entityDescription = config.entities[entityClass!!]
            if (entityDescription != null) {
                val annotationContent = entityDescription.attributes.values.asSequence()
                        .mapIndexed { i, attribute -> attribute.name to attributes!![i] }
                        .toMap()
                        .toMutableMap()
                val currentPosition = builder.length
                addAnnotation("e-${annotations.size}", annotationContent, currentPosition, builder.length - currentPosition)
            }

            entityClass = null
            attributes = null
        }
    }

    override fun word(indexes: List<String>) {
        val word = indexes[defaultIndex.columnIndex]
        val annotationContent = metaIndexes.map { it.name to indexes[it.columnIndex] }.toMap()
        if (annotationContent.isNotEmpty())
            addAnnotation("w-${annotations.size}", annotationContent, builder.length, word.length)
        builder.append(word)
                .append(' ')
    }


    private fun addAnnotation(id: String, content: Map<String, String>, from: Int, size: Int, subAnnotations: List<AnnotationPosition> = emptyList()) {
        annotations[id] = Annotation(id, content)
        positions.add(AnnotationPosition(id, MatchedRegion(from, size), subAnnotations))
    }

    override fun build(): ResultFormat.Snippet = ResultFormat.Snippet.StringWithMetadata(StringWithMetadata(builder.toString(), annotations, positions, queryMapping))
}