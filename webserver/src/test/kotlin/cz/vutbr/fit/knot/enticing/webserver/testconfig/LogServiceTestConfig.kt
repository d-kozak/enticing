package cz.vutbr.fit.knot.enticing.webserver.testconfig

import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class LogServiceTestConfig {

    @Bean
    fun loggerFactory() = SimpleStdoutLoggerFactory

}