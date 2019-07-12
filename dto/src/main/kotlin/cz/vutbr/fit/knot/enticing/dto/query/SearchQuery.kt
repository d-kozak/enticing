package cz.vutbr.fit.knot.enticing.dto.query

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.utils.Defaults
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

/**
 * Query to be processed by an IndexServer
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
        val snippetCount: Int,
        /**
         * Offset at which to start, for pagination
         */
        @field:Valid
        val offset: Offset = Offset(0, 0),
        /**
         * Which other indexes and entities should be included
         */
        @field:Valid
        val metadata: TextMetadata = Defaults.metadata,
        /**
         * What should be the response type
         */
        val responseType: ResponseType = Defaults.responseType,

        /**
         * What should be the format of the response
         */
        val responseFormat: ResponseFormat = Defaults.responseFormat,

        /**
         * What is the default index
         *
         * Default index is the one returned as text,
         * the others are considered the metadata of the default index
         */
        @field:NotBlank
        val defaultIndex: String = Defaults.defaultIndex
)


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
        @field:Positive
        val document: Int,
        /**
         * At which snippet in that document to start
         */
        @field:Positive
        val snippet: Int
)

/**
 * Types of responses
 */
enum class ResponseType {
    /**
     * What was matched by the query and it's surroundings
     */
    FULL,
    /**
     * Only return the values of identifiers used in the query
     *
     */
    @Incomplete("can only be used when EQL is in place")
    IDENTIFIERS
}

/**
 * Format of response
 */
enum class ResponseFormat {
    /**
     * Response should be a single string containing html
     */
    HTML,

    /**
     * Response should be AnnotatedText
     */
    ANNOTATED_TEXT;
}
