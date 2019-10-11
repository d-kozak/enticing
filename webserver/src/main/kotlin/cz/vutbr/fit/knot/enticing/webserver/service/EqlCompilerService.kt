package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import org.springframework.stereotype.Service

@Service
class EqlCompilerService(
        private val compiler: EqlCompiler
) {
    fun validateQuery(input: String, configuration: CorpusConfiguration) = compiler.parseAndAnalyzeQuery(input, configuration).errors
}