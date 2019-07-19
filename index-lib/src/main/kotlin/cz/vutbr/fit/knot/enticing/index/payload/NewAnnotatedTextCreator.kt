package cz.vutbr.fit.knot.enticing.index.payload

import cz.vutbr.fit.knot.enticing.dto.NewAnnotatedText
import cz.vutbr.fit.knot.enticing.dto.TextUnit
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetElement
import cz.vutbr.fit.knot.enticing.index.postprocess.SnippetPartsFields
import cz.vutbr.fit.knot.enticing.index.query.clamp
import it.unimi.dsi.util.Interval

fun createNewAnnotatedText(data: SnippetPartsFields, intervals: List<Interval>): NewAnnotatedText {
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
                        getElementsAt(items.elements.first().index to interval.left - 1, items)
                )
            }

            val match = TextUnit.QueryMatch(cz.vutbr.fit.knot.enticing.dto.Interval(0, 1), getElementsAt(interval.left to interval.right, items))
            elements.add(match)
            if (i < intervals.size - 1) {
                val nextStart = intervals[i + 1].left - 1
                elements.addAll(
                        getElementsAt(interval.right + 1 to nextStart, items)
                )
            }

            if (interval == intervals.last()) {
                elements.addAll(
                        getElementsAt(interval.right + 1 to items.elements.last().index, items)
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
    val relevantIntervals = intervals.filter { it.left in entityRange || it.right in entityRange }
            .map { it.clamp(minIndex, maxIndex) }
    if (relevantIntervals.isEmpty())
        return listOf(entity)

    val splitIntervals = relevantIntervals.mapIndexed { i, interval ->
        val prev = if (i > 0) relevantIntervals[i - 1] else Interval.valueOf(minIndex - 1)
        val next = if (i < relevantIntervals.size - 1) relevantIntervals[i + 1] else Interval.valueOf(maxIndex + 1)

        val includeLeft = prev.right + 1 <= interval.left - 1
        val includeRight = interval.right + 1 <= next.left - 1

        when {
            includeLeft && includeRight -> listOf(Interval.valueOf(prev.right + 1, interval.left - 1), Interval.valueOf(interval.left, interval.right), Interval.valueOf(interval.right + 1, next.left - 1))
            includeLeft -> listOf(Interval.valueOf(prev.right + 1, interval.left - 1), Interval.valueOf(interval.left, interval.right))
            includeRight -> listOf(Interval.valueOf(interval.left, interval.right), Interval.valueOf(interval.right + 1, next.left - 1))
            else -> listOf(Interval.valueOf(interval.left, interval.right))
        }
    }.flatten()

    return splitIntervals.map { entity.copy(index = it.left, words = entity.words.subList(it.left - entity.index, it.right - entity.index + 1)) }
}
