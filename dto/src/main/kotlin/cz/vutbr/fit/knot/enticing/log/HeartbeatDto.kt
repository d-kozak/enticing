package cz.vutbr.fit.knot.enticing.log

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

data class HeartbeatDto(
        @field:NotEmpty
        val componentId: String,
        val componentType: ComponentType,
        @field:Positive
        var timestamp: Long = System.currentTimeMillis()
)