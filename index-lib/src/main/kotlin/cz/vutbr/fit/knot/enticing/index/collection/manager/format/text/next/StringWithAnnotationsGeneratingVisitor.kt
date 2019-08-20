package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.next

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.text.Annotation
import cz.vutbr.fit.knot.enticing.dto.format.text.AnnotationPosition
import cz.vutbr.fit.knot.enticing.dto.format.text.QueryMapping
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import org.slf4j.LoggerFactory

class StringWithAnnotationsGeneratingVisitor(config: CorpusConfiguration, defaultIndexName: String) : TextFormatGeneratingListener(config, defaultIndexName) {

    private val log = LoggerFactory.getLogger(StringWithAnnotationsGeneratingVisitor::class.java)

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

    override fun word(indexes: List<String>) {
        builder.append(indexes[defaultIndex.columnIndex])
                .append(' ')
        if (metaIndexes.isNotEmpty()) {

        }
    }
}