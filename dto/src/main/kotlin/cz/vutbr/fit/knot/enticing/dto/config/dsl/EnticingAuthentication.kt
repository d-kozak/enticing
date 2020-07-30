package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationVisitor

/**
 * Configuration for the authentication
 */
data class EnticingAuthentication(
        var username: String = ""
) : EnticingConfigurationUnit {
    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitEnticingAuthentication(this)
    }
}