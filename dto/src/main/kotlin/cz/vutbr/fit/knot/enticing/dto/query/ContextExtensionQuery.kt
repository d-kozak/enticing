package cz.vutbr.fit.knot.enticing.dto.query

import java.util.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

data class ContextExtensionQuery(
        @field:NotEmpty
        val host: String,
        @field:NotEmpty
        val collection: String,
        val docId: UUID,
        @field:Positive
        val location: Long,
        @field:Positive
        val size: Long
)