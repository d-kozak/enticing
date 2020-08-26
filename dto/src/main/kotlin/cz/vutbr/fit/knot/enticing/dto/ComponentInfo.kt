package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.log.ComponentType
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

interface ComponentInfo {
    val id: Long
    val serverAddress: String
    val port: Int
    val type: ComponentType
}

data class BasicComponentInfo(
        @field:PositiveOrZero
        override val id: Long,
        @field:NotBlank
        override val serverAddress: String,
        @field:Positive
        override val port: Int,
        override val type: ComponentType
) : ComponentInfo

data class ExtendedComponentInfo(
        @field:Positive
        override val id: Long,
        @field:Positive
        val serverId: Long,
        @field:NotBlank
        override val serverAddress: String,
        @field:Positive
        override val port: Int,
        override val type: ComponentType,
        val lastHeartbeat: LocalDateTime?,
        val status: Status
) : ComponentInfo

enum class Status {
    STARTING,
    RUNNING,
    DEAD
}