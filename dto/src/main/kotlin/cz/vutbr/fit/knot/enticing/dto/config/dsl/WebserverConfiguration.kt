package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationVisitor

/**
 * Configuration of the webserver
 */
data class WebserverConfiguration(
        override var address: String = "",
        override var port: Int = 8080
) : ComponentConfiguration {
    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitWebserverConfiguration(this)
    }
}