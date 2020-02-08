package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.IndexServerConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.util.Validator
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.util.ValidatorImpl

class EnticingConfigurationValidator(val validator: Validator) : EnticingConfigurationListener, Validator by validator {

    override fun enterIndexServerConfiguration(configuration: IndexServerConfiguration) {
        if (configuration.corpus == null) {
            check(configuration.indexedDir != null) { "index server either has to be part of a corpus or has its own indexedDir" }
            check(configuration.mg4jDir != null) { "index server either has to be part of a corpus or has its own mg4jDir" }
            check(configuration.metadataConfiguration != null) { "index server either has to be part of a corpus or has its own metadata" }
        }
    }
}


fun EnticingConfiguration.validateOrFail() {
    val validator = EnticingConfigurationValidator(ValidatorImpl())
    this.walk(validator)
    if (validator.errors.isNotEmpty())
        throw IllegalStateException(validator.errors.joinToString("\n"))
}