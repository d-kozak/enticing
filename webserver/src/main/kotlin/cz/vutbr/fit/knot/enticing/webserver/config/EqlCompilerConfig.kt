package cz.vutbr.fit.knot.enticing.webserver.config

import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EqlCompilerConfig(val loggerFactory: LoggerFactory) {

    @Bean
    fun parser() = EqlCompiler(loggerFactory)
}