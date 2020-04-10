package cz.vutbr.fit.knot.enticing.management.managementservice.dto

import cz.vutbr.fit.knot.enticing.mx.ServerStatus
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

data class ServerInfo(
        @field:Positive
        val id: Long,
        @field:NotEmpty
        val address: String,
        @field:Positive
        val availableProcessors: Int,
        @field:Positive
        val totalPhysicalMemorySize: Long,
        val status: ServerStatus?
)