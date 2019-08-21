package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.next

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnit
import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnitList
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import org.slf4j.LoggerFactory

class TextUnitListGeneratingListener(config: CorpusConfiguration, defaultIndexName: String) : TextFormatGeneratingListener(config, defaultIndexName) {

    private val log = LoggerFactory.getLogger(TextUnitListGeneratingListener::class.java)

    private val resultList = mutableListOf<TextUnit>()

    private var attributes: List<String>? = null
    private var entityClass: String? = null
    private var wordsForEntity: MutableList<TextUnit.Word>? = null

    private var currentQueryInterval: Interval? = null
    private var unitsForQueryMatch: MutableList<TextUnit>? = null

    override fun matchStart(queryInterval: Interval) {
        if (currentQueryInterval != null || unitsForQueryMatch != null) {
            log.error("matchStart called while some match interval metadata are still present inside the listener, they will be overwritten")
        }
        currentQueryInterval = queryInterval
        unitsForQueryMatch = mutableListOf()
    }

    override fun entityStart(attributes: List<String>, entityClass: String) {
        if (this.attributes != null || this.entityClass != null || this.wordsForEntity?.isNotEmpty() == true) {
            log.error("entityStart called while some entity metadata are still present inside the listener, they will be overwritten")
        }
        this.attributes = attributes
        this.entityClass = entityClass
        this.wordsForEntity = mutableListOf()
    }

    override fun word(indexes: List<String>) {
        val word = TextUnit.Word(indexes)
        when {
            wordsForEntity != null -> wordsForEntity!!.add(word)
            unitsForQueryMatch != null -> unitsForQueryMatch!!.add(word)
            else -> resultList.add(word)
        }

    }

    override fun entityEnd() {
        if (attributes != null && entityClass != null && wordsForEntity?.isEmpty() == true) {
            val newEntity = TextUnit.Entity(attributes!!, entityClass!!, wordsForEntity!!)
            if (unitsForQueryMatch != null) {
                unitsForQueryMatch!!.add(newEntity)
            } else {
                resultList.add(newEntity)
            }
        } else {
            log.error("entityEnd called, but no data for entity have been collected")
        }
        attributes = null
        entityClass = null
        wordsForEntity = null
    }

    override fun matchEnd() {
        if (currentQueryInterval != null && unitsForQueryMatch?.isNotEmpty() == true) {
            resultList.add(TextUnit.QueryMatch(currentQueryInterval!!, unitsForQueryMatch!!))
        } else {
            log.error("matchEnd called, but no data for query match have been collected")
        }
        currentQueryInterval = null
        unitsForQueryMatch = null
    }

    override fun build(): ResultFormat.Snippet = ResultFormat.Snippet.TextUnitList(TextUnitList(resultList))
}