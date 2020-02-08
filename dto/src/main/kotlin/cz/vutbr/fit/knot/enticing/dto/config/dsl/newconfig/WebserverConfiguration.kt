package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor.EnticingConfigurationVisitor

/**
 * Configuration of the webserver
 */
data class WebserverConfiguration(
        override var address: String? = null
) : ComponentConfiguration {
    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitWebserverConfiguration(this)
    }
}