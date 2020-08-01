package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnit
import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnitList
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger

/**
 * Generates the TextUnitList format
 */
class TextUnitListGeneratingListener(config: MetadataConfiguration, defaultIndexName: String, interval: Interval, document: IndexedDocument, loggerFactory: LoggerFactory) : TextFormatGeneratingListener(config, defaultIndexName, interval, document) {

    private val logger = loggerFactory.logger { }

    private val resultList = mutableListOf<TextUnit>()

    private var attributes: List<String>? = null
    private var entityClass: String? = null
    private var wordsForEntity: MutableList<TextUnit.Word>? = null

    private var currentQueryInterval: Interval? = null
    private var unitsForQueryMatch: MutableList<TextUnit>? = null

    private val selectedIndexes: Set<Int> = config.indexes.values.map { it.columnIndex }.toSet()

    override fun onMatchStart(queryInterval: Interval) {
        if (currentQueryInterval != null || unitsForQueryMatch != null) {
            logger.warn("matchStart called while some match interval metadata are still present inside the listener, they will be overwritten")
        }
        currentQueryInterval = queryInterval
        unitsForQueryMatch = mutableListOf()
    }

    override fun onEntityStart(attributes: List<String>, entityClass: String) {
        if (this.attributes != null || this.entityClass != null || this.wordsForEntity?.isNotEmpty() == true) {
            logger.warn("entityStart called while some entity metadata are still present inside the listener, they will be overwritten")
        }
        if (entityClass in config.entities) {
            this.attributes = config.entities.getValue(entityClass).allAttributes.values.map { attributes[it.attributeIndex] }
            this.entityClass = entityClass
            this.wordsForEntity = mutableListOf()
        }
    }

    override fun onWord(indexes: List<String>) {
        val word = TextUnit.Word(indexes.filterIndexed { i, _ -> i in selectedIndexes })
        when {
            wordsForEntity != null -> wordsForEntity!!.add(word)
            unitsForQueryMatch != null -> unitsForQueryMatch!!.add(word)
            else -> resultList.add(word)
        }

    }

    override fun onEntityEnd() {
        if (attributes != null && entityClass != null && wordsForEntity?.isNotEmpty() == true) {
            val newEntity = TextUnit.Entity(attributes!!, entityClass!!, wordsForEntity!!)
            if (unitsForQueryMatch != null) {
                unitsForQueryMatch!!.add(newEntity)
            } else {
                resultList.add(newEntity)
            }
        } else {
            logger.info("entityEnd called, but no data for entity have been collected")
        }
        attributes = null
        entityClass = null
        wordsForEntity = null
    }

    override fun onMatchEnd() {
        if (currentQueryInterval != null && unitsForQueryMatch?.isNotEmpty() == true) {
            resultList.add(TextUnit.QueryMatch(currentQueryInterval!!, unitsForQueryMatch!!))
        } else {
            logger.info("matchEnd called, but no data for query match have been collected")
        }
        currentQueryInterval = null
        unitsForQueryMatch = null
    }

    override fun build(): ResultFormat.Snippet = ResultFormat.Snippet.TextUnitList(TextUnitList(resultList), location, size, canExtend)
}