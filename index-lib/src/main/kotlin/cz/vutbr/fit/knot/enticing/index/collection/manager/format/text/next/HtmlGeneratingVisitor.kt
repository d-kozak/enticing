package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.next

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument

class HtmlGeneratingVisitor(config: CorpusConfiguration, defaultIndexName: String, interval: Interval, document: IndexedDocument) : TextFormatGeneratingVisitor(config, defaultIndexName, interval, document) {

    private val builder = StringBuilder()

    override fun visitMatchStart(queryInterval: Interval) {
        builder.append("<b>")
    }

    override fun visitMatchEnd() {
        builder.append("</b>")
    }

    override fun visitWord(indexes: List<String>) {
        with(builder) {
            if (config.indexes.size > 1) {
                append("<span eql-word")
                for (index in metaIndexes) {
                    append(" eql-")
                    append(index.name)
                    append("=\"")
                    append(indexes[index.columnIndex])
                    append("\"")
                }
                append(">")
                append(indexes[defaultIndex.columnIndex])
                append("</span>")
            } else {
                append("<span>")
                append(indexes[config.indexes.values.first().columnIndex])
                append("</span>")
            }
        }
    }

    private var entityStarted = false

    override fun visitEntityStart(attributes: List<String>, entityClass: String) {
        with(builder) {
            val entity = config.entities[entityClass]
            if (entity != null) {
                entityStarted = true
                append("<span eql-entity")
                for (attribute in entity.attributes.values) {
                    append(" eql-")
                    append(attribute.name)
                    append("=\"")
                    append(attributes[attribute.attributeIndex])
                    append("\"")
                }
                append(">")
            }
        }
    }

    override fun visitEntityEnd() {
        if (entityStarted) {
            entityStarted = false
            builder.append("</span>")
        }
    }

    override fun build(): ResultFormat.Snippet = ResultFormat.Snippet.Html(builder.toString(), location, size, canExtend)
}