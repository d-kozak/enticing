package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.toCorpusConfig
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import org.springframework.stereotype.Service

@Service
class EqlCompilerService(
        private val compiler: EqlCompiler,
        private val queryService: QueryService

) {

    fun parseQuery(input: String, selectedSettings: Long) = compiler.parseAndAnalyzeQuery(input, queryService.format(selectedSettings).toCorpusConfig())
}