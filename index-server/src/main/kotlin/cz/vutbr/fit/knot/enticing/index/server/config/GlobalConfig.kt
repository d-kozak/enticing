package cz.vutbr.fit.knot.enticing.index.server.config

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexServerConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.prettyPrint
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.log.configureFor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GlobalConfig(
        @Value("\${config.file}") private val configFile: String,
        @Value("\${service.id}") private val address: String
) {

    private val log: Logger = LoggerFactory.getLogger(GlobalConfig::class.java)

    @Bean
    fun enticingConfiguration(): EnticingConfiguration {
        log.info("Loading configuration from $configFile")
        val config = executeScript<EnticingConfiguration>(configFile)
        log.info("Loaded config")
        log.info("\n${config.prettyPrint()}")
        config.validateOrFail()
        return config
    }

    @Bean
    fun logger(enticingConfiguration: EnticingConfiguration): MeasuringLogService {
        return enticingConfiguration.loggingConfiguration.configureFor(address, enticingConfiguration.managementServiceConfiguration.fullAddress)
    }

    @Bean
    fun indexServerConfiguration(config: EnticingConfiguration): IndexServerConfiguration = config.indexServerByAddress(address)

    @Bean
    fun metadataConfiguration(config: IndexServerConfiguration): MetadataConfiguration = config.metadataConfiguration
            ?: config.corpus.metadataConfiguration

}