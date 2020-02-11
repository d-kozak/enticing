package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.LoggingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.util.Validator
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.util.ValidatorImpl
import java.io.File

class EnticingConfigurationValidator(val validator: Validator) : EnticingConfigurationListener, Validator by validator {

    override fun enterCorpusConfiguration(configuration: CorpusConfiguration) {
        validator.check(configuration.metadataConfiguration.indexes.isNotEmpty()) { "at least one index should be present" }
    }

    override fun enterLoggingConfiguration(loggingConfiguration: LoggingConfiguration) {
        validator.checkNotEmpty(loggingConfiguration.rootDirectory, "rootDirectory")
        validator.checkDirectory(File(loggingConfiguration.rootDirectory), createIfNecessary = true)
    }
}


fun EnticingConfiguration.validateOrFail(): EnticingConfiguration {
    val validator = EnticingConfigurationValidator(ValidatorImpl())
    this.walk(validator)
    if (validator.errors.isNotEmpty())
        throw IllegalStateException(validator.errors.joinToString("\n"))
    return this
}