package cz.vutbr.fit.knot.enticing.webserver.config

import cz.vutbr.fit.knot.enticing.api.ManagementServiceApi
import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
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

@Configuration
class GlobalConfig(
        @Value("\${config.file}") private val configFile: String,
        @Value("\${service.id}") private val address: String,
        @Value("\${server.port}") private val port: Int
) {

    private val fullAddress: String = "$address:$port"

    private val log = org.slf4j.LoggerFactory.getLogger(GlobalConfig::class.java)

    @Bean
    fun enticingConfiguration(): EnticingConfiguration {
        log.info("Loading configuration from $configFile")
        val config = executeScript<EnticingConfiguration>(configFile)
        log.info("Loaded config")
        config.validateOrFail()
        config.prettyPrint()
        return config
    }

    @Bean
    fun managementApi(enticingConfiguration: EnticingConfiguration) = ManagementServiceApi(enticingConfiguration.managementServiceConfiguration.fullAddress, ComponentType.WEBSERVER, fullAddress, enticingConfiguration.loggingConfiguration.loggerFactoryFor("$address-webserver"))

    @Bean
    fun loggerFactory(enticingConfiguration: EnticingConfiguration, managementServiceApi: ManagementServiceApi): LoggerFactory {
        return enticingConfiguration.loggingConfiguration.loggerFactoryFor("$address-webserver", managementServiceApi)
    }

    @Bean
    fun monitoringService(loggerFactory: LoggerFactory) = ServerMonitoringService(fullAddress, ComponentType.WEBSERVER, loggerFactory)
}