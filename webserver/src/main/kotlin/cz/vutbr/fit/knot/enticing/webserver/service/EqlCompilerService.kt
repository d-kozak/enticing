package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import org.springframework.stereotype.Service

@Service
class EqlCompilerService(
        private val compiler: EqlCompiler

) {

    fun parseQuery(input: String) = compiler.parse(input)
}