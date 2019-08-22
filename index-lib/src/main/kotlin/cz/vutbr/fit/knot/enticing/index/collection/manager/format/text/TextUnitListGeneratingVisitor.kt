package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnit
import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnitList
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import org.slf4j.LoggerFactory

class TextUnitListGeneratingVisitor(config: CorpusConfiguration, defaultIndexName: String, interval: Interval, document: IndexedDocument) : TextFormatGeneratingVisitor(config, defaultIndexName, interval, document) {

    private val log = LoggerFactory.getLogger(TextUnitListGeneratingVisitor::class.java)

    private val resultList = mutableListOf<TextUnit>()

    private var attributes: List<String>? = null
    private var entityClass: String? = null
    private var wordsForEntity: MutableList<TextUnit.Word>? = null

    private var currentQueryInterval: Interval? = null
    private var unitsForQueryMatch: MutableList<TextUnit>? = null

    override fun visitMatchStart(queryInterval: Interval) {
        if (currentQueryInterval != null || unitsForQueryMatch != null) {
            log.warn("matchStart called while some match interval metadata are still present inside the listener, they will be overwritten")
        }
        currentQueryInterval = queryInterval
        unitsForQueryMatch = mutableListOf()
    }

    override fun visitEntityStart(attributes: List<String>, entityClass: String) {
        if (this.attributes != null || this.entityClass != null || this.wordsForEntity?.isNotEmpty() == true) {
            log.warn("entityStart called while some entity metadata are still present inside the listener, they will be overwritten")
        }
        this.attributes = attributes
        this.entityClass = entityClass
        this.wordsForEntity = mutableListOf()
    }

    override fun visitWord(indexes: List<String>) {
        val word = TextUnit.Word(indexes)
        when {
            wordsForEntity != null -> wordsForEntity!!.add(word)
            unitsForQueryMatch != null -> unitsForQueryMatch!!.add(word)
            else -> resultList.add(word)
        }

    }

    override fun visitEntityEnd() {
        if (attributes != null && entityClass != null && wordsForEntity?.isNotEmpty() == true) {
            val newEntity = TextUnit.Entity(attributes!!, entityClass!!, wordsForEntity!!)
            if (unitsForQueryMatch != null) {
                unitsForQueryMatch!!.add(newEntity)
            } else {
                resultList.add(newEntity)
            }
        } else {
            log.warn("entityEnd called, but no data for entity have been collected")
        }
        attributes = null
        entityClass = null
        wordsForEntity = null
    }

    override fun visitMatchEnd() {
        if (currentQueryInterval != null && unitsForQueryMatch?.isNotEmpty() == true) {
            resultList.add(TextUnit.QueryMatch(currentQueryInterval!!, unitsForQueryMatch!!))
        } else {
            log.warn("matchEnd called, but no data for query match have been collected")
        }
        currentQueryInterval = null
        unitsForQueryMatch = null
    }

    override fun build(): ResultFormat.Snippet = ResultFormat.Snippet.TextUnitList(TextUnitList(resultList), location, size, canExtend)
}