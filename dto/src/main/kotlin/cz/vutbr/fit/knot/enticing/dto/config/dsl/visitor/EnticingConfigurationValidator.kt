package cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.*
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.util.Validator
import cz.vutbr.fit.knot.enticing.dto.config.dsl.util.ValidatorImpl
import java.io.File

/**
 * Validates the EnticingConfiguration, collects all errors in the validator object
 */
class EnticingConfigurationValidator(validator: Validator) : EnticingConfigurationListener, Validator by validator {

    override fun enterEnticingConfiguration(configuration: EnticingConfiguration) {
        checkNotEmpty(configuration.localHome, "local home")
    }

    override fun enterCorpusConfiguration(configuration: CorpusConfiguration) {
        check(configuration.metadataConfiguration.indexes.isNotEmpty()) { "at least one index should be present" }
    }

    override fun enterLoggingConfiguration(loggingConfiguration: LoggingConfiguration) {
        if (checkNotEmpty(loggingConfiguration.rootDirectory, "rootDirectory"))
            checkDirectory(File(loggingConfiguration.rootDirectory), createIfNecessary = true)
    }

    override fun enterEnticingAuthentication(enticingAuthentication: EnticingAuthentication) {
        checkNotEmpty(enticingAuthentication.username, "username")
    }

    override fun enterCorpusSourceConfiguration(corpusSourceConfiguration: CorpusSourceConfiguration) {
        checkNotEmpty(corpusSourceConfiguration.server, "corpus source server")
        checkNotEmpty(corpusSourceConfiguration.directory, "corpus source directory")
    }

    override fun enterDeploymentConfiguration(deploymentConfiguration: DeploymentConfiguration) {
        checkNotEmpty(deploymentConfiguration.server, "deployment server")
        checkNotEmpty(deploymentConfiguration.repository, "deployment repository")
        checkNotEmpty(deploymentConfiguration.configurationScript, "configuration script")
        checkNotEmpty(deploymentConfiguration.buildId, "Build id")
    }

    override fun enterMetadataConfiguration(configuration: MetadataConfiguration) = validateMetadataConfiguration(configuration)
}


/**
 * Validate given configuration unit
 * @throws IllegalStateException if the configuration is invalid
 */
fun MetadataConfiguration.validateOrFail(): MetadataConfiguration {
    val validator = EnticingConfigurationValidator(ValidatorImpl())
    validator.enterMetadataConfiguration(this)
    if (validator.errors.isNotEmpty())
        throw IllegalStateException(validator.errors.joinToString("\n"))
    return this
}

/**
 * Validate given configuration unit
 * @throws IllegalStateException if the configuration is invalid
 */
fun EnticingConfiguration.validateOrFail(): EnticingConfiguration {
    val validator = EnticingConfigurationValidator(ValidatorImpl())
    this.walk(validator)
    if (validator.errors.isNotEmpty())
        throw IllegalStateException(validator.errors.joinToString("\n"))
    return this
}