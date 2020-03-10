package cz.vutbr.fit.knot.enticing.management.managementservice.dto

import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.mx.ServerStatus
import javax.validation.constraints.NotEmpty

data class ServerInfo(
        @field:NotEmpty
        val componentId: String,
        val componentType: ComponentType,
        val availableProcessors: Int,
        val totalPhysicalMemorySize: Long,
        val status: ServerStatus?
)