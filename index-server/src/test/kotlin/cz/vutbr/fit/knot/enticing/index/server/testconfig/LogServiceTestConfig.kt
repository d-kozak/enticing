package cz.vutbr.fit.knot.enticing.index.server.testconfig

import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class LogServiceTestConfig {

    @Bean
    fun testLoggerFactory() = SimpleStdoutLoggerFactory

}