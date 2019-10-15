package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.webserver.dto.QueryValidationReply
import org.springframework.stereotype.Service

@Service
class EqlCompilerService(
        private val compiler: EqlCompiler
) {
    fun validateQuery(input: String, configuration: CorpusConfiguration): QueryValidationReply {
        val (ast, errors) = compiler.parseAndAnalyzeQuery(input, configuration)
        return QueryValidationReply(ast.toMgj4Query(), errors)
    }
}