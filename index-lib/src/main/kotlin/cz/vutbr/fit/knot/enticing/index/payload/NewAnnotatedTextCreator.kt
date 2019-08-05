package cz.vutbr.fit.knot.enticing.index.payload

import cz.vutbr.fit.knot.enticing.dto.Interval
import cz.vutbr.fit.knot.enticing.dto.NewAnnotatedText
import cz.vutbr.fit.knot.enticing.dto.TextUnit
import cz.vutbr.fit.knot.enticing.dto.annotation.Warning
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.index.postprocess.DocumentElement
import cz.vutbr.fit.knot.enticing.index.postprocess.StructuredDocumentContent

fun createNewAnnotatedText(data: StructuredDocumentContent, intervals: List<Interval>, corpusConfiguration: CorpusConfiguration): NewAnnotatedText {
    if (data.elements.isEmpty()) {
        return NewAnnotatedText()
    }
    val items = splitEntities(intervals, data)
    check(items.elements.isNotEmpty()) { "Items should never be empty" }

    val textUnits = if (intervals.isNotEmpty()) {
        intervals.mapIndexed { i, interval ->
            val elements = mutableListOf<TextUnit>()
            if (interval == intervals.first()) {
                elements.addAll(
                        getElementsAt(items.elements.first().index to interval.from - 1, items)
                )
            }

            val match = TextUnit.QueryMatch(Interval.valueOf(0, 1), getElementsAt(interval.from to interval.to, items))
            elements.add(match)
            if (i < intervals.size - 1) {
                val nextStart = intervals[i + 1].from - 1
                elements.addAll(
                        getElementsAt(interval.to + 1 to nextStart, items)
                )
            }

            if (interval == intervals.last()) {
                elements.addAll(
                        getElementsAt(interval.to + 1 to items.elements.last().index, items)
                )
            }
            elements
        }.flatten()
    } else {
        @Warning("When the beginning of the interval was incorrectly set to 0, it was returning the first element from the SnippetPartsFields (containing only subset of the document) instead of failing, should be fixed")
        getElementsAt(items.first().index to items.last().index, items)
    }
    val tokenIndex = corpusConfiguration.indexes.getValue("token").columnIndex
    return NewAnnotatedText(
            textUnits.filter { it !is TextUnit.Word || (it.indexes[tokenIndex] != "¶" && it.indexes[tokenIndex] != "§") }
    )
}

fun getElementsAt(interval: Pair<Int, Int>, elements: StructuredDocumentContent): List<TextUnit> {
    val (left, right) = interval
    return (left..right).map { elements[it] }.map { toTextUnit(it) }
}

fun toTextUnit(element: DocumentElement): TextUnit = when (element) {
    is DocumentElement.Word -> TextUnit.Word(element.indexes)
    is DocumentElement.Entity -> TextUnit.Entity(element.entityInfo, element.entityClass, element.words.map { toTextUnit(it) as TextUnit.Word })
}

internal fun splitEntities(intervals: List<Interval>, data: StructuredDocumentContent): StructuredDocumentContent {
    val newElements = data.elements.flatMap { element ->
        if (element is DocumentElement.Entity) {
            splitEntity(element, intervals)
        } else listOf(element)
    }
    return data.copy(elements = newElements)
}

fun splitEntity(entity: DocumentElement.Entity, intervals: List<Interval>): List<DocumentElement> {
    val minIndex = entity.index
    val maxIndex = entity.words.last().index
    val entityRange = minIndex..maxIndex
    val relevantIntervals = intervals.filter { it.from in entityRange || it.to in entityRange }
            .map { it.clamp(minIndex, maxIndex) }
    if (relevantIntervals.isEmpty())
        return listOf(entity)

    val splitIntervals = relevantIntervals.mapIndexed { i, interval ->
        val prev = if (i > 0) relevantIntervals[i - 1] else Interval.valueOf(minIndex - 1)
        val next = if (i < relevantIntervals.size - 1) relevantIntervals[i + 1] else Interval.valueOf(maxIndex + 1)

        val includeLeft = prev.to + 1 <= interval.from - 1
        val includeRight = interval.to + 1 <= next.from - 1

        when {
            includeLeft && includeRight -> listOf(Interval.valueOf(prev.to + 1, interval.from - 1), Interval.valueOf(interval.from, interval.to), Interval.valueOf(interval.to + 1, next.from - 1))
            includeLeft -> listOf(Interval.valueOf(prev.to + 1, interval.from - 1), Interval.valueOf(interval.from, interval.to))
            includeRight -> listOf(Interval.valueOf(interval.from, interval.to), Interval.valueOf(interval.to + 1, next.from - 1))
            else -> listOf(Interval.valueOf(interval.from, interval.to))
        }
    }.flatten()

    return splitIntervals.map { entity.copy(index = it.from, words = entity.words.subList(it.from - entity.index, it.to - entity.index + 1)) }
}
