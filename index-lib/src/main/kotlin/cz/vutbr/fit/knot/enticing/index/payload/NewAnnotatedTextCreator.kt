package cz.vutbr.fit.knot.enticing.index.payload

import cz.vutbr.fit.knot.enticing.dto.NewAnnotatedText
import cz.vutbr.fit.knot.enticing.dto.TextUnit
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetElement
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetPartsFields
import it.unimi.dsi.util.Interval

fun createNewAnnotatedText(data: SnippetPartsFields, intervals: List<Interval>): NewAnnotatedText {
    val items = splitEntities(intervals, data)
    check(items.elements.isNotEmpty()) { "Items should never be empty" }

    val textUnits = if (intervals.isNotEmpty()) {
        intervals.mapIndexed { i, interval ->
            val elements = mutableListOf<TextUnit>()
            if (interval == intervals.first()) {
                elements.addAll(
                        getElementsAt(0 to interval.left - 1, items)
                )
            }

            val match = TextUnit.QueryMatch(cz.vutbr.fit.knot.enticing.dto.Interval(0, 1), getElementsAt(interval.left to interval.right, items) as List<TextUnit.ElementaryTextUnit>)
            elements.add(match)
            if (i < intervals.size - 1) {
                val nextStart = intervals[i + 1].left - 1
                elements.addAll(
                        getElementsAt(interval.right + 1 to nextStart, items)
                )
            }

            if (interval == intervals.last()) {
                elements.addAll(
                        getElementsAt(interval.right + 1 to items.last().index, items)
                )
            }
            elements
        }.flatten()
    } else getElementsAt(0 to items.last().index, items)
    return NewAnnotatedText(textUnits)
}

fun getElementsAt(interval: Pair<Int, Int>, elements: SnippetPartsFields): List<TextUnit> {
    val (left, right) = interval
    return (left..right).map { elements[it] }.map { toTextUnit(it) }
}

fun toTextUnit(element: SnippetElement): TextUnit = when (element) {
    is SnippetElement.Word -> TextUnit.Word(element.indexes)
    is SnippetElement.Entity -> TextUnit.Entity(element.entityInfo, element.entityClass, element.words.map { toTextUnit(it) as TextUnit.Word })
}

internal fun splitEntities(intervals: List<Interval>, data: SnippetPartsFields): SnippetPartsFields {
    val newElements = data.elements.flatMap { element ->
        if (element is SnippetElement.Entity) {
            splitEntity(element, intervals)
        } else listOf(element)
    }
    return data.copy(elements = newElements)
}

fun splitEntity(entity: SnippetElement.Entity, intervals: List<Interval>): List<SnippetElement> {
    val minIndex = entity.index
    val maxIndex = entity.words.last().index
    val entityRange = minIndex..maxIndex
    val relevantIntervals = intervals.asSequence()
            .flatMap { sequenceOf(it.left to true, it.right to false) }
            .filter { it.first in entityRange }
            .toList()
    if (relevantIntervals.isEmpty())
        return listOf(entity)


    return relevantIntervals
            .mapIndexed { i, (current, isStart) ->
                val prev = if (i > 0) relevantIntervals[i - 1] else minIndex to true
                val next = if (i < relevantIntervals.size - 1) relevantIntervals[i + 1] else maxIndex to false
                val left = entity.copy(
                        index = if (prev.second) prev.first else prev.first + 1,
                        words = entity.words.subList(prev.first - entity.index + if (prev.second) 0 else 1, current - entity.index + if (isStart) 0 else 1)
                )
                val right = entity.copy(
                        index = if (isStart) current else current + 1,
                        words = entity.words.subList(current - entity.index + if (isStart) 0 else 1, next.first - entity.index + if (next.second) 0 else 1)
                )
                listOf(left, right)
            }.flatten()
}
