package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.*
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.AttributeConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.EntityConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.IndexConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration

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
}