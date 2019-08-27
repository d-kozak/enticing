package cz.vutbr.fit.knot.enticing.webserver.dto

import cz.vutbr.fit.knot.enticing.dto.Offset
import cz.vutbr.fit.knot.enticing.dto.SearchQuery

data class LastQuery(
        val query: SearchQuery,
        val selectedSettings: Long,
        val offset: Map<String, Map<String, Offset>>
)