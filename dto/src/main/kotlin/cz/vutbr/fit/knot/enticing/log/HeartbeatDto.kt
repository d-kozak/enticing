package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.mx.ServerStatus
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

data class HeartbeatDto(
        @field:NotEmpty
        val fullAddress: String,
        val componentType: ComponentType,
        val status: ServerStatus? = null,
        @field:Positive
        var timestamp: Long = System.currentTimeMillis(),
        var isAlive: Boolean = true
)