package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.mx.ServerStatus
import javax.validation.constraints.NotEmpty

data class HeartbeatDto(
        @field:NotEmpty
        val fullAddress: String,
        val componentType: ComponentType,
        val status: ServerStatus
)