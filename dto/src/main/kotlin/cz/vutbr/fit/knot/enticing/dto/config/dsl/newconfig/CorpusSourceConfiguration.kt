package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor.EnticingConfigurationVisitor

/**
 * Information about the location of the original files of that corpus before distribution
 */
data class CorpusSourceConfiguration(
        var server: String = "",
        var directory: String = ""
) : EnticingConfigurationUnit {
    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitCorpusSourceConfiguration(this)
    }
}