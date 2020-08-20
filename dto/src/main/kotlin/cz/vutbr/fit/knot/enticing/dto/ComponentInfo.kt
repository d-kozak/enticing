package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.log.ComponentType
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

data class BasicComponentInfo(
        @field:PositiveOrZero
        val id: Long,
        @field:NotBlank
        val serverAddress: String,
        @field:Positive
        val port: Int,
        val type: ComponentType
)

data class ExtendedComponentInfo(
        @field:Positive
        val id: Long,
        @field:Positive
        val serverId: Long,
        @field:NotBlank
        val serverAddress: String,
        @field:Positive
        val port: Int,
        val type: ComponentType,
        val lastHeartbeat: LocalDateTime,
        val status: ComponentStatus
)

enum class ComponentStatus {
    RUNNING,
    DEAD
}