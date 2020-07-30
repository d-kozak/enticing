package cz.vutbr.fit.knot.enticing.dto.format.result

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import cz.vutbr.fit.knot.enticing.dto.TextFormat
import cz.vutbr.fit.knot.enticing.dto.format.text.StringWithMetadata
import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnit
import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnitList
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive


/**
 * create an empty snippet for a given text format
 */
fun emptySnippet(format: TextFormat): ResultFormat.Snippet = when (format) {
    TextFormat.PLAIN_TEXT -> ResultFormat.Snippet.PlainText("", 0, 0, false)
    TextFormat.HTML -> ResultFormat.Snippet.Html("", 0, 0, false)
    TextFormat.STRING_WITH_METADATA -> ResultFormat.Snippet.StringWithMetadata(StringWithMetadata("", emptyMap(), emptySet(), emptySet()), 0, 0, false)
    TextFormat.TEXT_UNIT_LIST -> ResultFormat.Snippet.TextUnitList(TextUnitList(emptyList()), 0, 0, false)
}

/**
 * The actual data in the snippet
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        JsonSubTypes.Type(ResultFormat.IdentifierList::class, name = "identifiers"),
        JsonSubTypes.Type(ResultFormat.Snippet::class, name = "full")
)
sealed class ResultFormat {
    /**
     * List of identifiers from the query and their values
     */
    data class IdentifierList(@field:Valid val list: List<Identifier>) : ResultFormat()

    /**
     * Part of the document that was matched
     */
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "type"
    )
    @JsonSubTypes(
            JsonSubTypes.Type(Snippet.PlainText::class, name = "plain"),
            JsonSubTypes.Type(Snippet.Html::class, name = "html"),
            JsonSubTypes.Type(Snippet.StringWithMetadata::class, name = "annotated"),
            JsonSubTypes.Type(Snippet.TextUnitList::class, name = "new")
    )
    sealed class Snippet : ResultFormat() {
        abstract val location: Int
        abstract val size: Int
        abstract val canExtend: Boolean

        /**
         * Just the text from the default index
         */
        data class PlainText(val content: String, @field:Positive override val location: Int, @field:Positive override val size: Int, override val canExtend: Boolean) : Snippet()

        /**
         * Simple html format
         */
        data class Html(val content: String, @field:Positive override val location: Int, @field:Positive override val size: Int, override val canExtend: Boolean) : Snippet()

        /**
         * Text with annotations
         */
        data class StringWithMetadata(@field:Valid val content: cz.vutbr.fit.knot.enticing.dto.format.text.StringWithMetadata, @field:Positive override val location: Int, @field:Positive override val size: Int, override val canExtend: Boolean) : Snippet()

        /**
         * New annotated format
         */
        data class TextUnitList(@field:Valid val content: cz.vutbr.fit.knot.enticing.dto.format.text.TextUnitList, @field:Positive override val location: Int, @field:Positive override val size: Int, override val canExtend: Boolean) : Snippet()
    }
}

/**
 * Identifier from the query and it's value
 */
data class Identifier(
        @field:NotBlank
        val identifier: String,
        @field:Valid
        val snippet: ResultFormat.Snippet
)

fun List<TextUnit>.toRawText(tokenIndex: Int): String = buildString {
    for ((i, unit) in this@toRawText.withIndex()) {
        when (unit) {
            is TextUnit.Word -> append(unit.indexes[tokenIndex])
            is TextUnit.Entity -> append(unit.words.toRawText(tokenIndex))
            is TextUnit.QueryMatch -> append("<b>${unit.content.toRawText(tokenIndex)}</b>")
        }
        if (i != this@toRawText.size - 1) append(' ')
    }
}