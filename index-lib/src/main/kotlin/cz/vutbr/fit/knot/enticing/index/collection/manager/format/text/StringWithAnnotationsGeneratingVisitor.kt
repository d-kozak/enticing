package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text


import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.text.*
import cz.vutbr.fit.knot.enticing.dto.format.text.Annotation
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger

class StringWithAnnotationsGeneratingVisitor(config: MetadataConfiguration, defaultIndexName: String, interval: Interval, document: IndexedDocument, loggerFactory: LoggerFactory) : TextFormatGeneratingVisitor(config, defaultIndexName, interval, document) {

    private val logger = loggerFactory.logger { }

    private val builder = StringBuilder()
    private val annotations = mutableMapOf<String, Annotation>()
    private val positions = mutableSetOf<AnnotationPosition>()
    private val queryMapping = mutableSetOf<QueryMapping>()


    private var matchStartPosition = -1
    private var queryInterval: Interval? = null

    override fun visitMatchStart(queryInterval: Interval) {
        this.matchStartPosition = if (builder.isNotEmpty()) builder.length + 1 else builder.length
        this.queryInterval = queryInterval
    }

    override fun visitMatchEnd() {
        if (matchStartPosition != -1 && queryInterval != null) {
            queryMapping.add(QueryMapping(matchStartPosition to builder.length - 1, queryInterval!!.from to queryInterval!!.to))
            matchStartPosition = -1
            queryInterval = null
        } else {
            logger.error("matchEnd executed, but there was no start position")
        }
    }


    private var attributes: List<String>? = null
    private var entityClass: String? = null
    private var entityStartPosition = -1

    override fun visitEntityStart(attributes: List<String>, entityClass: String) {
        this.entityStartPosition = if (builder.isNotEmpty()) builder.length + 1 else builder.length
        this.attributes = attributes
        this.entityClass = entityClass
    }

    override fun visitEntityEnd() {
        if (attributes != null && entityClass != null && entityStartPosition != -1) {
            val entityDescription = config.entities[entityClass!!]
            if (entityDescription != null) {
                val annotationContent = entityDescription.ownAttributes.values.asSequence()
                        .mapIndexed { i, attribute -> attribute.name to attributes!![i] }
                        .toMap()
                        .toMutableMap()
                if (config.entityIndexName !in annotationContent) {
                    annotationContent[config.entityIndexName] = entityClass!!
                }
                addAnnotation("e-${annotations.size}", annotationContent, entityStartPosition, builder.length - entityStartPosition)
            }

            entityStartPosition = -1
            entityClass = null
            attributes = null
        }
    }

    override fun visitWord(indexes: List<String>) {
        if (builder.isNotEmpty()) builder.append(' ')
        val word = indexes[defaultIndex.columnIndex]
        val annotationContent = metaIndexes.map { it.name to indexes[it.columnIndex] }.toMap()
        if (annotationContent.isNotEmpty())
            addAnnotation("w-${annotations.size}", annotationContent, builder.length, word.length)
        builder.append(word)
    }


    private fun addAnnotation(id: String, content: Map<String, String>, from: Int, size: Int, subAnnotations: List<AnnotationPosition> = emptyList()) {
        annotations[id] = Annotation(id, content)
        positions.add(AnnotationPosition(id, MatchedRegion(from, size), subAnnotations))
    }

    override fun build(): ResultFormat.Snippet = ResultFormat.Snippet.StringWithMetadata(StringWithMetadata(builder.toString(), annotations, positions, queryMapping), location, size, canExtend)

}