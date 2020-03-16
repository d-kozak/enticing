package cz.vutbr.fit.knot.enticing.dto.format.text

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class TextUnitList(
        @field:Valid
        @field:NotEmpty
        val content: List<TextUnit>
) {
    constructor(vararg textUnits: TextUnit) : this(textUnits.toList())
}


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

fun List<TextUnit>.words(): Sequence<TextUnit.Word> = sequence<TextUnit.Word> {
    for (unit in this@words) {
        when (unit) {
            is TextUnit.Word -> yield(unit)
            is TextUnit.Entity -> yieldAll(unit.words)
            is TextUnit.QueryMatch -> yieldAll(unit.content.words())
        }
    }
}

fun List<TextUnit>.matches() = this.filterIsInstance(TextUnit.QueryMatch::class.java)