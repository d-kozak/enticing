package cz.vutbr.fit.knot.enticing.index.collection.manager.format.text.next

import cz.vutbr.fit.knot.enticing.dto.AstNode
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument
import cz.vutbr.fit.knot.enticing.index.boundary.TokenReader

@Incomplete("start using AST when EQL is in place")
fun generatePlainText(document: IndexedDocument, filteredConfig: CorpusConfiguration, defaultIndex: String, astNode: AstNode?, interval: Interval?): ResultFormat.Snippet.PlainText {
    val defaultColumnIndex = filteredConfig.indexes[defaultIndex]?.columnIndex
            ?: throw IllegalArgumentException("Default index $defaultIndex not found")
    val defaultContent = document.content[defaultColumnIndex]
    val text = if (interval == null) defaultContent
    else {
        val (from, to) = interval
        val reader = TokenReader(defaultContent, ' ')
        buildString {
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
    }
    return ResultFormat.Snippet.PlainText(text)
}

