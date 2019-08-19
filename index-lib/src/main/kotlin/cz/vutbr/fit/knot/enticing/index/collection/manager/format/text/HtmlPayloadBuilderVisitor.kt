package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text

import cz.vutbr.fit.knot.enticing.dto.Mg4jQuery
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.DocumentElement

class HtmlPayloadBuilderVisitor(
        config: CorpusConfiguration, query: Mg4jQuery, intervals: List<Interval>
) : AbstractPayloadBuilderVisitor<ResultFormat>(config, query, intervals) {

    override fun visitMatchStart() {
        builder.append("<b>")
    }

    override fun visitWord(word: DocumentElement.Word) {
        with(builder) {
            if (metaIndexes.isNotEmpty()) {
                append("<span eql-word")
                for (index in metaIndexes) {
                    if (index != defaultIndex) {
                        val value = word[index]
                        append(" eql-")
                        append(index)
                        append("=")
                        append('"')
                        append(value)
                        append('"')
                    }
                }
                append(">")
                append(word[defaultIndex])
                append("</span>")
            } else {
                append(word[defaultIndex])
            }
        }
    }

    override fun visitEntity(entity: DocumentElement.Entity) {
        with(builder) {
            val eqlEntity = config.entities[entity.entityClass]
            if (eqlEntity != null) {
                append("<span eql-entity")
                for (attribute in eqlEntity.attributes.values) {
                    val name = attribute.name
                    val value = entity.entityInfo[attribute.columnIndex]
                    append(" eql-")
                    append(name)
                    append("=")
                    append('"')
                    append(value)
                    append('"')
                }
                append(">")
            }
            for (word in entity.words) {
                visitWord(word)
                if (isMatchEnd(word.index)) {
                    append("</b>")
                }
            }
            if (eqlEntity != null)
                append("</span>")
        }
    }

    override fun visitMatchEnd() {
        builder.append("</b>")
    }

    override fun visitSeparator() {
        builder.append(' ')
    }

    override fun getResult(): ResultFormat = ResultFormat.FullResponse.Html(builder.toString())
}