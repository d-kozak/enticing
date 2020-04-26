package cz.vutbr.fit.knot.enticing.management.managementservice.dto

import cz.vutbr.fit.knot.enticing.log.ComponentType
import java.time.LocalDateTime
import javax.validation.constraints.Positive

data class ComponentInfo(
        @field:Positive
        val id: Long,
        @field:Positive
        val serverId: Long,
        val serverAddress: String,
        @field:Positive
        val port: Int,
        val type: ComponentType,
        val lastHeartbeat: LocalDateTime)