package cz.vutbr.fit.knot.enticing.index.payload

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.Annotation
import cz.vutbr.fit.knot.enticing.dto.annotation.Temporary
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetElement
import org.slf4j.LoggerFactory


class JsonPayloadBuilderVisitor(config: CorpusConfiguration, query: Mg4jQuery, intervals: List<Interval>
) : AbstractPayloadBuilderVisitor<Payload>(config, query, intervals) {

    private val log = LoggerFactory.getLogger(JsonPayloadBuilderVisitor::class.java)

    private val annotations = mutableMapOf<String, Annotation>()
    private val positions = mutableListOf<AnnotationPosition>()
    private val queryMapping = mutableListOf<QueryMapping>()

    @Temporary("this is just a dummy value until we know the real queryMatch (waiting for EQL)")
    private val queryMatch = 0 to 1

    private var startPosition = -1

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
        val currentPosition = builder.length

        val subAnnotations = mutableListOf<AnnotationPosition>()

        for ((i, word) in entity.words.withIndex()) {
            val token = word[query.defaultIndex]

            val annotationContent = metaIndexes.map { it to word[it] }.toMap()
            if (annotationContent.isNotEmpty()) {
                val key = "w-${annotations.size}"
                annotations[key] = Annotation(key, annotationContent)
                subAnnotations.add(AnnotationPosition(key, MatchedRegion(builder.length, token.length)))
            }
            builder.append(token)
            // necessary to manually visit the end of match if it happens within entity :X
            if (isMatchEnd(word.index)) {
                visitMatchEnd()
            }
            if (i != entity.words.size - 1) {
                builder.append(' ')
            }
        }

        val entityDescription = config.entities[entity.entityClass]
        if (entityDescription == null) {
            log.error("could not find entity with class ${entity.entityClass} in ${config.entities}")
            return
        }

        if (entityDescription.attributes.size != entity.entityInfo.size) {
            log.error("Inconsistent entity attributes and entityInfo, ${entityDescription.attributes}, ${entity.entityInfo}")
            return
        }

        val annotationContent = entityDescription.attributes.values.asSequence()
                .mapIndexed { i, attribute -> attribute.name to entity.entityInfo[i] }
                .toMap()
                .toMutableMap()
        // add nertag if necessary
        if (annotationContent[config.entityMapping.entityIndex] == null) {
            annotationContent[config.entityMapping.entityIndex] = entity.entityClass
        }


        addAnnotation("e-${annotations.size}", annotationContent, currentPosition, builder.length - currentPosition, subAnnotations)
    }

    override fun visitMatchEnd() {
        if (startPosition != -1) {
            queryMapping.add(QueryMapping(startPosition to builder.length, queryMatch))
            startPosition = -1
        } else {
            log.error("visitMatchEnd executed, but there was no start position")
        }
    }

    override fun visitSeparator() {
        builder.append(' ')
    }

    private fun addAnnotation(id: String, content: Map<String, String>, from: Int, size: Int, subAnnotations: List<AnnotationPosition> = emptyList()) {
        annotations[id] = Annotation(id, content)
        positions.add(AnnotationPosition(id, MatchedRegion(from, size), subAnnotations))
    }

    override fun getResult(): Payload {
        @Temporary("The result should actually never be empty, this is here only until the query processing is not finalized")
        val text = if (builder.isNotEmpty()) builder.toString() else "!!!EMPTY!!!"
        return Payload.FullResponse.Annotated(
                AnnotatedText(text, annotations, positions, queryMapping))
    }
}

