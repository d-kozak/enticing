package cz.vutbr.fit.knot.enticing.dto

data class NewAnnotatedText(
        val content: List<TextUnit>
) {
    constructor(vararg textUnits: TextUnit) : this(textUnits.toList())
}

data class Interval(val from: Int, val to: Int)

sealed class TextUnit {
    data class Word(val indexes: List<String>) : TextUnit(), ElementaryTextUnit {
        constructor(vararg indexes: String) : this(indexes.toList())
    }

    data class Entity(val attributes: List<String>, val entityClass: String, val words: List<Word>) : TextUnit(), ElementaryTextUnit
    data class QueryMatch(val queryMatch: Interval, val content: List<ElementaryTextUnit>) : TextUnit()

    interface ElementaryTextUnit
}