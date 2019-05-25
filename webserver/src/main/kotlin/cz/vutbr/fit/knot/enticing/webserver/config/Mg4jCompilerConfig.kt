package cz.vutbr.fit.knot.enticing.webserver.config

import cz.vutbr.fit.knot.enticing.mg4j.compiler.parser.Mg4jParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Mg4jCompilerConfig {

    @Bean
    fun parser() = Mg4jParser()
}