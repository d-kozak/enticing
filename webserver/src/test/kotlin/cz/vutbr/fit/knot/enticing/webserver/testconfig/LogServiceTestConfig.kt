package cz.vutbr.fit.knot.enticing.webserver.testconfig

import cz.vutbr.fit.knot.enticing.log.StdoutLogService
import cz.vutbr.fit.knot.enticing.log.measuring
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class LogServiceTestConfig {

    @Bean
    fun measuringLogService() = StdoutLogService(loadedConfiguration.loggingConfiguration).measuring(loadedConfiguration.loggingConfiguration)

}