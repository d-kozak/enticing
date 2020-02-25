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
        var hearthBeatConfiguration: HearthBeatConfiguration = HearthBeatConfiguration(false)
) : ComponentConfiguration {


    companion object {
        const val DEFAULT_PORT = 5628
    }

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