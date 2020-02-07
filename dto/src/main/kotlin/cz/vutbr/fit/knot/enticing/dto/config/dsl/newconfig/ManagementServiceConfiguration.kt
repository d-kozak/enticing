package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

/**
 * Configuration of the manager service
 */
class ManagementServiceConfiguration : AbstractComponentConfiguration() {
    /**
     * whether a hearthBeat service should be started
     */
    var hearthBeat: Boolean = false
}