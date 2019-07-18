package cz.vutbr.fit.knot.enticing.dto

import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.PositiveOrZero

data class NewAnnotatedText(
        @field:Valid
        @field:NotEmpty
        val content: List<TextUnit>
) {
    constructor(vararg textUnits: TextUnit) : this(textUnits.toList())
}

data class Interval(
        @field:PositiveOrZero
        val from: Int,
        @field:PositiveOrZero
        val to: Int)

sealed class TextUnit {
    data class Word(
            @field:NotEmpty
            val indexes: List<String>
    ) : TextUnit(), ElementaryTextUnit {
        constructor(vararg indexes: String) : this(indexes.toList())
    }

    data class Entity(
            @field:NotEmpty
            val attributes: List<String>,
            @field:NotBlank
            val entityClass: String,
            @field:NotEmpty
            val words: List<Word>) : TextUnit(), ElementaryTextUnit

    data class QueryMatch(
            @field:Valid
            val queryMatch: Interval,
            @field:NotEmpty
            @field:Valid
            val content: List<ElementaryTextUnit>) : TextUnit()

    interface ElementaryTextUnit
}