package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationVisitor

/**
 * Configuration of the manager service
 */
data class ManagementServiceConfiguration(
        override var address: String? = null,
        /**
         * Hearthbeat configuration
         */
        var hearthBeatConfiguration: HearthBeatConfiguration = HearthBeatConfiguration(false)
) : ComponentConfiguration {
    fun hearthBeat(block: HearthBeatConfiguration.() -> Unit) {
        hearthBeatConfiguration = HearthBeatConfiguration(true).apply(block)
    }

    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitManagementConfiguration(this)
    }
}

/**
 * configuration of the hearthbeat functionality
 */
data class HearthBeatConfiguration(
        var isEnabled: Boolean = false,
        var period: Long = 1_000
)