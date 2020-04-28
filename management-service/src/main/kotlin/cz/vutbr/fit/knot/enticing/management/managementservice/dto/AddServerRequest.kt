package cz.vutbr.fit.knot.enticing.management.managementservice.dto

import javax.validation.constraints.NotEmpty

data class AddServerRequest(
        @field:NotEmpty
        val url: String
)