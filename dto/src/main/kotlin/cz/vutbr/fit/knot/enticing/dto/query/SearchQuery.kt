package cz.vutbr.fit.knot.enticing.dto.query

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

data class SearchQuery(
        @field:NotBlank
        val query: String,
        @field:Positive
        val snippetCount: Int,
        @field:Valid
        val offset: Offset,
        val metadata: Metadata,
        val responseType: ResponseType = ResponseType.SNIPPET,
        val responseFormat: ResponseFormat = ResponseFormat.JSON,
        @field:NotEmpty
        val defaultIndex: String = "token"
)

typealias EntityId = String


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        JsonSubTypes.Type(Metadata.Predefined::class, name = "predef"),
        JsonSubTypes.Type(Metadata.ExactDefinition::class, name = "exact")
)
sealed class Metadata {
    data class Predefined(val value: String) : Metadata()
    data class ExactDefinition(
            val entities: Entities,
            val indexes: Indexes
    )
}

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        JsonSubTypes.Type(Entities.All::class, name = "all"),
        JsonSubTypes.Type(Entities.ExactDefinition::class, name = "def")
)
sealed class Entities {
    @JsonTypeName("all")
    class All : Entities()

    @JsonTypeName("def")
    data class ExactDefinition(
            val entities: Map<EntityId, Indexes>
    ) : Entities()
}

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        JsonSubTypes.Type(Indexes.All::class, name = "all"),
        JsonSubTypes.Type(Indexes.ExactDefinition::class, name = "def")
)
sealed class Indexes {
    @JsonTypeName("all")
    class All : Indexes()

    @JsonTypeName("def")
    data class ExactDefinition(val names: List<String>) : Indexes()
}

data class Offset(
        @field:Positive
        val document: Long,
        @field:Positive
        val snippet: Int
)

enum class ResponseType {
    SNIPPET,
    MATCH
}

enum class ResponseFormat {
    HTML,
    JSON;
}
