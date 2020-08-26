package cz.vutbr.fit.knot.enticing.management.managementservice.dto

import cz.vutbr.fit.knot.enticing.log.ComponentType
import javax.validation.constraints.Positive

data class AddComponentRequest(
        @field:Positive
        val serverId: Long,
        @field:Positive
        val port: Int,
        val type: ComponentType
)

