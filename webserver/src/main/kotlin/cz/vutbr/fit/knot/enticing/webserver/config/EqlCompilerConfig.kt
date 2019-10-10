package cz.vutbr.fit.knot.enticing.webserver.config

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.corpusConfig
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EqlCompilerConfig {

    @Bean
    fun parser() = EqlCompiler()
}