package cz.vutbr.fit.knot.enticing.webserver.service


import cz.vutbr.fit.knot.enticing.mg4j.compiler.ast.MockNode
import cz.vutbr.fit.knot.enticing.mg4j.compiler.dto.ParsedQuery
import cz.vutbr.fit.knot.enticing.mg4j.compiler.parser.Mg4jParser
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test


class Mg4jCompilerServiceTest {

    private val parseMock = mockk<Mg4jParser>()

    @Test
    fun `parseQuery calls mg4j parser`() {
        val service = Mg4jCompilerService(parseMock)
        every { parseMock.parse("foo") } returns ParsedQuery(MockNode())

        service.parseQuery("foo")

        verify(exactly = 1) { parseMock.parse("foo") }
    }
}