package cz.vutbr.fit.knot.enticing.management.managementservice

import cz.vutbr.fit.knot.enticing.dto.utils.asJsonObject
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import org.springframework.test.web.servlet.ResultActions

internal val apiBasePath = "/api/v1"

inline fun <reified T> ResultActions.extractPaginatedItems(): List<T> = (this.andReturn()
        .response.contentAsString.asJsonObject()["content"])
        .map { it.toString().toDto<T>() }