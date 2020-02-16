package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.*
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.AttributeConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.EntityConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.IndexConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration

class EnticingConfigurationWalker(val listener: EnticingConfigurationListener) : EnticingConfigurationVisitor {

    override fun visitEnticingConfiguration(configuration: EnticingConfiguration) {
        listener.enterEnticingConfiguration(configuration)
        configuration.managementServiceConfiguration.accept(this)
        configuration.webserverConfiguration.accept(this)
        configuration.corpuses.values.forEach { it.accept(this) }
        configuration.loggingConfiguration.accept(this)
        configuration.authentication.accept(this)
        configuration.deploymentConfiguration.accept(this)
    }

    override fun visitManagementConfiguration(configuration: ManagementServiceConfiguration) {
        listener.enterManagementConfiguration(configuration)
    }

    override fun visitIndexServerConfiguration(configuration: IndexServerConfiguration) {
        listener.enterIndexServerConfiguration(configuration)
        if (configuration.metadataConfiguration != null) listener.enterMetadataConfiguration(configuration.metadataConfiguration!!)
    }

    override fun visitWebserverConfiguration(configuration: WebserverConfiguration) {
        listener.enterWebserverConfiguration(configuration)
    }

    override fun visitCorpusConfiguration(configuration: CorpusConfiguration) {
        listener.enterCorpusConfiguration(configuration)
        configuration.indexServers.forEach { it.accept(this) }
        listener.enterMetadataConfiguration(configuration.metadataConfiguration)
        configuration.corpusSourceConfiguration.accept(this)
    }

    override fun visitMetadataConfiguration(configuration: MetadataConfiguration) {
        listener.enterMetadataConfiguration(configuration)
        configuration.indexes.values.forEach { it.accept(this) }
        configuration.entities.values.forEach { it.accept(this) }
    }

    override fun visitIndexConfiguration(configuration: IndexConfiguration) {
        listener.enterIndexConfiguration(configuration)
    }

    override fun visitEntityConfiguration(configuration: EntityConfiguration) {
        listener.enterEntityConfiguration(configuration)
        configuration.attributes.values.forEach { it.accept(this) }
    }

    override fun visitAttributeConfiguration(configuration: AttributeConfiguration) {
        listener.enterAttributeConfiguration(configuration)
    }

    override fun visitCorpusSourceConfiguration(corpusSourceConfiguration: CorpusSourceConfiguration) {
        listener.enterCorpusSourceConfiguration(corpusSourceConfiguration)
    }

    override fun visitLoggingConfiguration(loggingConfiguration: LoggingConfiguration) {
        listener.enterLoggingConfiguration(loggingConfiguration)
    }

    override fun visitEnticingAuthentication(enticingAuthentication: EnticingAuthentication) {
        listener.enterEnticingAuthentication(enticingAuthentication)
    }

    override fun visitDeploymentConfiguration(deploymentConfiguration: DeploymentConfiguration) {
        listener.enterDeploymentConfiguration(deploymentConfiguration)
    }
}

fun EnticingConfiguration.walk(listener: EnticingConfigurationListener): EnticingConfigurationWalker {
    val walker = EnticingConfigurationWalker(listener)
    this.accept(walker)
    return walker
}
