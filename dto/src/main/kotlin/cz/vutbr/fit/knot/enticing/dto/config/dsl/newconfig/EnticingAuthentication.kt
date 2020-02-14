package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor.EnticingConfigurationVisitor

data class EnticingAuthentication(
        var username: String = ""
) : EnticingConfigurationUnit {
    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitEnticingAuthentication(this)
    }
}