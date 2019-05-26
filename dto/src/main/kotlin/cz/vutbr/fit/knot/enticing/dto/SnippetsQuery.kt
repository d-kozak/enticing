package cz.vutbr.fit.knot.enticing.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import cz.vutbr.fit.knot.enticing.mg4j.compiler.ast.QueryNode
import javax.validation.constraints.Positive

data class SnippetsQuery(
        @field:Positive
        val snippetsCount: Int,
        val query: QueryNode,
        val format: ResponseFormat
)

data class ResponseFormat(
        val nertags: NertagsFormat,
        val attributes: AttributesFormat
)

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
sealed class NertagsFormat {
    data class SimpleDefinition(val def: String) : NertagsFormat()
    data class DetailedDefinition(val nertags: Map<String, List<String>>)
}

sealed class AttributesFormat {
    data class SimpleDefinition(val def: String) : AttributesFormat()
    data class DetailedDefinition(val attributes: List<String>) : AttributesFormat()
}