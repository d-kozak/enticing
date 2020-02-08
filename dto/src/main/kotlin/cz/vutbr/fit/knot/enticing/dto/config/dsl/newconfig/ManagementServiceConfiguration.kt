package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

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
}

/**
 * configuration of the hearthbeat functionality
 */
data class HearthBeatConfiguration(
        var isEnabled: Boolean = false,
        var period: Long = 1_000
)