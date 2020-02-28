package cz.vutbr.fit.knot.enticing.index.server.testconfig

import cz.vutbr.fit.knot.enticing.log.StdoutLogService
import cz.vutbr.fit.knot.enticing.log.measuring
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
class LogServiceTestConfig {

    @Bean
    @Primary
    fun measuringLogService() = StdoutLogService(loadedConfiguration.loggingConfiguration).measuring()

}