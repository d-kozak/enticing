package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.boundary.TokenReader

fun generatePlainText(document: IndexedDocument, filteredConfig: MetadataConfiguration, defaultIndex: String, interval: Interval?): ResultFormat.Snippet.PlainText {
    val defaultColumnIndex = filteredConfig.indexes[defaultIndex]?.columnIndex
            ?: throw IllegalArgumentException("Default index $defaultIndex not found")
    val defaultContent = document.content[defaultColumnIndex].joinToString(" ")
    return if (interval == null) {
        ResultFormat.Snippet.PlainText(defaultContent, 0, document.size, false)
    } else {
        val (from, to) = interval
        val reader = TokenReader(defaultContent, ' ')
        val text = buildString {
            for ((i, word) in reader.withIndex()) {
                if (i < from) continue
                if (i > to) break
                append(word)
                append(' ')
            }
            if (isNotEmpty()) {
                setLength(length - 1) // remove last space
            }
        }
        ResultFormat.Snippet.PlainText(text, from, to - from + 1, from > 0 || to < document.size - 1)
    }
}

