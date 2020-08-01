package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument

/**
 * Generates the HTML text format
 */
class HtmlGeneratingListener(config: MetadataConfiguration, defaultIndexName: String, interval: Interval, document: IndexedDocument) : TextFormatGeneratingListener(config, defaultIndexName, interval, document) {

    private val builder = StringBuilder()

    override fun onMatchStart(queryInterval: Interval) {
        builder.append("<b>")
    }

    override fun onMatchEnd() {
        builder.append("</b>")
    }

    override fun onWord(indexes: List<String>) {
        with(builder) {
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
        }
    }

    private var entityStarted = false

    override fun onEntityStart(attributes: List<String>, entityClass: String) {
        with(builder) {
            val entity = config.entities[entityClass]
            if (entity != null) {
                entityStarted = true
                append("<span eql-entity")
                for (attribute in entity.allAttributes.values) {
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

    override fun onEntityEnd() {
        if (entityStarted) {
            entityStarted = false
            builder.append("</span>")
        }
    }

    override fun build(): ResultFormat.Snippet = ResultFormat.Snippet.Html(builder.toString(), location, size, canExtend)
}