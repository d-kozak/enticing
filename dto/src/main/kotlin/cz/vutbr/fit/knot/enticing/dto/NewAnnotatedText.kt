package cz.vutbr.fit.knot.enticing.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
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


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        JsonSubTypes.Type(TextUnit.Word::class, name = "word"),
        JsonSubTypes.Type(TextUnit.Entity::class, name = "entity"),
        JsonSubTypes.Type(TextUnit.QueryMatch::class, name = "queryMatch")
)
sealed class TextUnit {
    data class Word(
            @field:NotEmpty
            val indexes: List<String>
    ) : TextUnit() {
        constructor(vararg indexes: String) : this(indexes.toList())
    }

    data class Entity(
            @field:NotEmpty
            val attributes: List<String>,
            @field:NotBlank
            val entityClass: String,
            @field:NotEmpty
            val words: List<Word>) : TextUnit()

    data class QueryMatch(
            @field:Valid
            val queryMatch: Interval,
            @field:NotEmpty
            @field:Valid
            val content: List<TextUnit>) : TextUnit()
}