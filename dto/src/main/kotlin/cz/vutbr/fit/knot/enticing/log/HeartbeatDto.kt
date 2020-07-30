package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.mx.ServerStatus
import javax.validation.constraints.NotEmpty

/**
 * Heartbeat message that is periodically sent to the management server by all other components
 */
data class HeartbeatDto(
        @field:NotEmpty
        val fullAddress: String,
        val componentType: ComponentType,
        val status: ServerStatus
)