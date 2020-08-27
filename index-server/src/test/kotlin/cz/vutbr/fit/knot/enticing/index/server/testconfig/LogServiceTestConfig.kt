package cz.vutbr.fit.knot.enticing.index.server.testconfig

import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.mx.ServerMonitoringService
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class LogServiceTestConfig {

    @Bean
    fun testLoggerFactory() = SimpleStdoutLoggerFactory

    @Bean
    fun testMonitoringService() = ServerMonitoringService("foo.bar", ComponentType.INDEX_SERVER, "release", SimpleStdoutLoggerFactory)

}