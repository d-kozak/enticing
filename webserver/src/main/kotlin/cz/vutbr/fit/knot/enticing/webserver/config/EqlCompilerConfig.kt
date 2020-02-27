package cz.vutbr.fit.knot.enticing.webserver.config

import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EqlCompilerConfig(val logService: MeasuringLogService) {

    @Bean
    fun parser() = EqlCompiler(logService)
}