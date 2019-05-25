package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.mg4j.compiler.parser.Mg4jParser
import org.springframework.stereotype.Service

@Service
class Mg4jCompilerService(
        private val parser: Mg4jParser

) {

    fun parseQuery(input: String) = parser.parse(input)
}