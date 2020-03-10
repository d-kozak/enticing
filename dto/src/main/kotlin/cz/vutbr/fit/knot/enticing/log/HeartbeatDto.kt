package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.mx.ServerStatus
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty

data class HeartbeatDto(
        @field:NotEmpty
        val fullAddress: String,
        val componentType: ComponentType,
        val status: ServerStatus? = null,
        var timestamp: LocalDateTime = LocalDateTime.now(),
        var isAlive: Boolean = true
)