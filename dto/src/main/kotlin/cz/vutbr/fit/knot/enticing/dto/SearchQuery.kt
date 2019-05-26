package cz.vutbr.fit.knot.enticing.dto

import javax.validation.constraints.NotNull

data class SearchQuery(
        @field:NotNull
        val foo: String
)