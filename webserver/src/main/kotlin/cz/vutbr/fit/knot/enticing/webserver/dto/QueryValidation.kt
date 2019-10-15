package cz.vutbr.fit.knot.enticing.webserver.dto


import cz.vutbr.fit.knot.enticing.eql.compiler.parser.CompilerError
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

data class QueryValidationRequest(
        @field:NotEmpty
        val query: String,
        @field:Positive
        val settingsId: Long
)

data class QueryValidationReply(
        @field:NotEmpty
        val mg4jQuery: String,
        val errors: List<CompilerError>
)