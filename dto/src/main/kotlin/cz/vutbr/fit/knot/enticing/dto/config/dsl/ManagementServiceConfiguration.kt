package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationVisitor

/**
 * Configuration of the manager service
 */
data class ManagementServiceConfiguration(
        override var address: String = "",
        override var port: Int = DEFAULT_PORT,
        /**
         * Hearthbeat configuration
         */
        var heartbeatConfiguration: HeartbeatConfiguration = HeartbeatConfiguration(false)
) : ComponentConfiguration {


    companion object {
        const val DEFAULT_PORT = 5628
    }

    fun heartbeat(block: HeartbeatConfiguration.() -> Unit) {
        heartbeatConfiguration = HeartbeatConfiguration(true).apply(block)
    }

    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitManagementConfiguration(this)
    }
}

/**
 * configuration of the hearthbeat functionality
 */
data class HeartbeatConfiguration(
        var isEnabled: Boolean = false,
        var period: Long = 1_000
)