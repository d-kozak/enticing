package cz.vutbr.fit.knot.enticing.index.payload

import cz.vutbr.fit.knot.enticing.dto.Mg4jQuery
import cz.vutbr.fit.knot.enticing.dto.Payload
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetElement
import it.unimi.dsi.util.Interval

class HtmlPayloadBuilderVisitor(
        config: CorpusConfiguration, query: Mg4jQuery, intervals: List<Interval>
) : AbstractPayloadBuilderVisitor<Payload>(config, query, intervals) {

    override fun visitMatchStart() {
        builder.append("<b>")
    }

    override fun visitWord(word: SnippetElement.Word) {
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

    override fun visitEntity(entity: SnippetElement.Entity) {
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

    override fun getResult(): Payload = Payload.FullResponse.Html(builder.toString())
}