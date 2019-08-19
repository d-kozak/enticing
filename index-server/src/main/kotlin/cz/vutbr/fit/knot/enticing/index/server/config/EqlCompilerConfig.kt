package cz.vutbr.fit.knot.enticing.index.server.config


import cz.vutbr.fit.knot.enticing.eql.compiler.parser.EqlCompiler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EqlCompilerConfig {

    @Bean
    fun compiler() = EqlCompiler()
}