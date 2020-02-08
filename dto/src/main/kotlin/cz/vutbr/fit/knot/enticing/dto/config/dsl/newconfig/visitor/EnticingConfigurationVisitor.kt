package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.*
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.AttributeConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.EntityConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.IndexConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration

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
}

interface EnticingConfigurationVisitee {
    fun accept(visitor: EnticingConfigurationVisitor)
}