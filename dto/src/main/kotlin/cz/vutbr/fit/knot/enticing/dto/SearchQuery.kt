package cz.vutbr.fit.knot.enticing.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

/**
 * Query to be processed by an IndexServer
 *
 * Used both on the webserver and IndexServer interface
 */
data class SearchQuery(
        /**
         * mg4j query to execute
         */
        @field:NotBlank
        val query: String,
        /**
         * Number of snippets to be returned
         */
        @field:Positive
        override val snippetCount: Int = Defaults.snippetCount,
        /**
         * Offset at which to start, for pagination, null means start from the beginning
         */
        @field:Valid
        val offset: Map<String, Offset>? = null,
        /**
         * Which other indexes and entities should be included
         */
        @field:Valid
        override val metadata: TextMetadata = Defaults.metadata,
        /**
         * What should be the response type
         */
        val resultFormat: ResultFormat = Defaults.resultFormat,

        /**
         * What should be the format of the response
         */
        override val textFormat: TextFormat = Defaults.textFormat,

        /**
         * What is the default index
         *
         * Default index is the one returned as text,
         * the others are considered the metadata of the default index
         */
        @field:NotBlank
        override val defaultIndex: String = Defaults.defaultIndex,
        @JsonIgnore
        var eqlAst: AstNode? = null
) : GeneralFormatInfo, Query<SearchQuery> {
    override fun updateSnippetCount(newSnippetCount: Int): SearchQuery = copy(snippetCount = newSnippetCount).also {
        it.eqlAst = this.eqlAst?.deepCopy()
    }
}


/**
 * What metadata should be included in the response
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        JsonSubTypes.Type(TextMetadata.Predefined::class, name = "predef"),
        JsonSubTypes.Type(TextMetadata.ExactDefinition::class, name = "exact")
)
sealed class TextMetadata {
    /**
     * Currently supported are "all" and "none"
     */
    data class Predefined(val value: String) : TextMetadata()

    data class ExactDefinition(
            val entities: Entities,
            val indexes: Indexes
    ) : TextMetadata()
}

typealias EntityId = String

/**
 * Which entities should be included in the response
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        JsonSubTypes.Type(Entities.Predefined::class, name = "predef"),
        JsonSubTypes.Type(Entities.ExactDefinition::class, name = "exact")
)
sealed class Entities {
    /**
     * Currently supported are "all" and "none"
     */
    data class Predefined(val value: String) : Entities()

    data class ExactDefinition(val entities: Map<EntityId, Indexes>) : Entities()
}

/**
 * Which indexes should be included
 *
 * Default index is included automatically
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        JsonSubTypes.Type(Indexes.Predefined::class, name = "predef"),
        JsonSubTypes.Type(Indexes.ExactDefinition::class, name = "exact")
)
sealed class Indexes {
    /**
     * Currently supported are "all" and "none"
     */
    data class Predefined(val value: String) : Indexes()

    data class ExactDefinition(val names: List<String>) : Indexes()
}

/**
 * For pagination
 */
data class Offset(

        /**
         * At which document to start next
         */
        @field:PositiveOrZero
        val document: Int,
        /**
         * At which snippet in that document to start
         */
        @field:PositiveOrZero
        val snippet: Int
)

/**
 * Types of responses
 */
enum class ResultFormat {
    /**
     * What was matched by the query and it's surroundings
     */
    SNIPPET,
    /**
     * Only return the values of identifiers used in the query
     *
     */
    @Incomplete("can only be used when EQL is in place")
    IDENTIFIER_LIST
}

/**
 * Format of response
 */
enum class TextFormat {
    /**
     * Just the text from the default index
     */
    PLAIN_TEXT,
    /**
     * Response should be a single string containing html
     */
    HTML,

    /**
     * Response should be AnnotatedText
     */
    STRING_WITH_METADATA,

    /**
     * New format of annotated text, eventually meant to replace it
     */
    TEXT_UNIT_LIST;
}
