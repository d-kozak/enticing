package cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.*
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.AttributeConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.EntityConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.IndexConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration

interface EnticingConfigurationListener {
    fun enterEnticingConfiguration(configuration: EnticingConfiguration) {

    }

    fun enterManagementConfiguration(configuration: ManagementServiceConfiguration) {

    }

    fun enterIndexServerConfiguration(configuration: IndexServerConfiguration) {

    }

    fun enterWebserverConfiguration(configuration: WebserverConfiguration) {

    }

    fun enterCorpusConfiguration(configuration: CorpusConfiguration) {

    }

    fun enterMetadataConfiguration(configuration: MetadataConfiguration) {

    }

    fun enterIndexConfiguration(configuration: IndexConfiguration) {

    }

    fun enterEntityConfiguration(configuration: EntityConfiguration) {

    }

    fun enterAttributeConfiguration(configuration: AttributeConfiguration) {

    }

    fun enterCorpusSourceConfiguration(corpusSourceConfiguration: CorpusSourceConfiguration) {

    }

    fun enterLoggingConfiguration(loggingConfiguration: LoggingConfiguration) {

    }

    fun enterEnticingAuthentication(enticingAuthentication: EnticingAuthentication) {

    }

    fun enterDeploymentConfiguration(deploymentConfiguration: DeploymentConfiguration) {

    }
}