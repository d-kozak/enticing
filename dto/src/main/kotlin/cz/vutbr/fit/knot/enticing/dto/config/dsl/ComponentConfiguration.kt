package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.ComponentAddress


/**
 * Configuration for a deployable component
 */
interface ComponentConfiguration : EnticingConfigurationUnit {
    /**
     * Server on which this component should be started
     * null means that it should be started on this machine
     */
    var address: String

    var port: Int

    val fullAddress: ComponentAddress
        get() = ComponentAddress(address, port)
}

