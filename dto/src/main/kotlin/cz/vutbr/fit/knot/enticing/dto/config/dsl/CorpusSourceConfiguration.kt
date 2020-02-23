package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationVisitor

/**
 * Information about the location of the original files of that corpus before distribution
 */
data class CorpusSourceConfiguration(
        var server: String = "",
        var directory: String = "",
        /**
         * how many collections should be created on each server during distribution
         */
        var collectionsPerServer: Int = 5,
        /**
         * how many mg4j files to take
         */
        var fileLimit: Int = Int.MAX_VALUE
) : EnticingConfigurationUnit {
    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitCorpusSourceConfiguration(this)
    }
}