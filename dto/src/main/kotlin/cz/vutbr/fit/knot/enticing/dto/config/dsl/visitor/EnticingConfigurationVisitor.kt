package cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.*
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.AttributeConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.EntityConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.IndexConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration

/**
 * Visitor for the EnticingConfiguration
 */
interface EnticingConfigurationVisitor {

    fun visitEnticingConfiguration(configuration: EnticingConfiguration) {

    }

    fun visitManagementConfiguration(configuration: ManagementServiceConfiguration) {

    }

    fun visitIndexServerConfiguration(configuration: IndexServerConfiguration) {

    }

    fun visitWebserverConfiguration(configuration: WebserverConfiguration) {

    }

    fun visitCorpusConfiguration(configuration: CorpusConfiguration) {

    }

    fun visitMetadataConfiguration(configuration: MetadataConfiguration) {

    }

    fun visitIndexConfiguration(configuration: IndexConfiguration) {

    }

    fun visitEntityConfiguration(configuration: EntityConfiguration) {

    }

    fun visitAttributeConfiguration(configuration: AttributeConfiguration) {

    }

    fun visitCorpusSourceConfiguration(corpusSourceConfiguration: CorpusSourceConfiguration) {

    }

    fun visitLoggingConfiguration(loggingConfiguration: LoggingConfiguration) {

    }

    fun visitEnticingAuthentication(enticingAuthentication: EnticingAuthentication) {

    }

    fun visitDeploymentConfiguration(deploymentConfiguration: DeploymentConfiguration) {

    }
}

interface EnticingConfigurationVisitee {
    fun accept(visitor: EnticingConfigurationVisitor)
}