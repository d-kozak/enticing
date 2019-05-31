package cz.vutbr.fit.knot.enticing.webserver.service


import cz.vutbr.fit.knot.enticing.eql.compiler.ast.MockNode
import cz.vutbr.fit.knot.enticing.eql.compiler.dto.ParsedQuery
import cz.vutbr.fit.knot.enticing.eql.compiler.parser.EqlCompiler
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test


class EqlCompilerServiceTest {

    private val parseMock = mockk<EqlCompiler>()

    @Test
    fun `parseQuery calls eql parser`() {
        val service = EqlCompilerService(parseMock)
        every { parseMock.parse("foo") } returns ParsedQuery(MockNode())

        service.parseQuery("foo")

        verify(exactly = 1) { parseMock.parse("foo") }
    }
}