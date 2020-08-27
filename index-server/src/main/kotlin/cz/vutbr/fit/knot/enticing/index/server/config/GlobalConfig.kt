package cz.vutbr.fit.knot.enticing.index.server.config

import cz.vutbr.fit.knot.enticing.api.ManagementServiceApi
import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexServerConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.prettyPrint
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.loggerFactoryFor
import cz.vutbr.fit.knot.enticing.mx.ServerMonitoringService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

/**
 * Creates global configuration beans used by other components
 */
@Configuration
class GlobalConfig(
        @Value("\${config.file}") private val configFile: String,
        @Value("\${service.id}") private val address: String,
        @Value("\${build.id}") private val buildId: String,
        @Value("\${server.port}") private val port: Int
) {

    @Cleanup("use component address instead")
    private val fullAddress: String = "$address:$port"

    private val log = org.slf4j.LoggerFactory.getLogger(GlobalConfig::class.java)

    /**
     * The whole configuration object
     */
    @Bean
    fun enticingConfiguration(): EnticingConfiguration {
        log.info("Loading configuration from $configFile")
        val config = executeScript<EnticingConfiguration>(configFile)
        log.info("Loaded config")
        // update build id, the one in the file might not be accurate
        config.deploymentConfiguration.buildId = buildId
        config.validateOrFail()
        config.prettyPrint()
        return config
    }

    /**
     * Proxy object to contact the management service
     */
    @Bean
    fun managementApi(enticingConfiguration: EnticingConfiguration): ManagementServiceApi = ManagementServiceApi(enticingConfiguration.managementServiceConfiguration.fullAddress, ComponentType.INDEX_SERVER, fullAddress, enticingConfiguration.loggingConfiguration.loggerFactoryFor("$address-webserver"))

    /**
     * Logger factory to configure loggers for each class
     */
    @Bean
    @Primary
    fun loggerFactory(enticingConfiguration: EnticingConfiguration, managementServiceApi: ManagementServiceApi): LoggerFactory {
        return enticingConfiguration.loggingConfiguration.loggerFactoryFor("$address-webserver", managementServiceApi)
    }

    /**
     * Configuration for this particular IndexServer, extracted from the global config
     */
    @Bean
    fun indexServerConfiguration(config: EnticingConfiguration): IndexServerConfiguration = config.indexServerByAddress(address)

    /**
     * Metadata configuration for this index server
     */
    @Bean
    fun metadataConfiguration(config: IndexServerConfiguration): MetadataConfiguration = config.metadataConfiguration
            ?: config.corpus.metadataConfiguration

    /**
     * Service for monitoring of the underlying server
     */
    @Bean
    fun monitoringService(loggerFactory: LoggerFactory) = ServerMonitoringService(fullAddress, ComponentType.INDEX_SERVER, buildId, loggerFactory)

}