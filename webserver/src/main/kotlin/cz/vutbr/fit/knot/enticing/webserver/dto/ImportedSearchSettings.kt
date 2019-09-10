package cz.vutbr.fit.knot.enticing.webserver.dto

import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.entity.validation.UrlCollection
import javax.validation.constraints.NotEmpty

data class ImportedSearchSettings(
        @field:NotEmpty
        val name: String,
        @field:NotEmpty
        val annotationDataServer: String,
        @field:NotEmpty
        val annotationServer: String,
        @field:NotEmpty
        @field:UrlCollection
        val servers: Set<String>
)

fun ImportedSearchSettings.toEntity() = SearchSettings(name = name, annotationDataServer = annotationDataServer, annotationServer = annotationServer, servers = servers)