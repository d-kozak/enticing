package cz.vutbr.fit.knot.enticing.webserver.service


import cz.vutbr.fit.knot.enticing.dto.CorpusFormat
import cz.vutbr.fit.knot.enticing.dto.PureMgj4Node
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.dto.ParsedQuery
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test


class EqlCompilerServiceTest {

    private val parseMock = mockk<EqlCompiler>()
    private val queryMock = mockk<QueryService>()

    @Test
    fun `parseQuery calls eql parser`() {
        val service = EqlCompilerService(parseMock, queryMock)
        every { parseMock.parseAndAnalyzeQuery("foo", any()) } returns ParsedQuery(PureMgj4Node("foo"))
        every { queryMock.format(5) } returns CorpusFormat("dummy", emptyMap(), emptyMap())

        service.parseQuery("foo", 5)

        verify(exactly = 1) { parseMock.parseAndAnalyzeQuery("foo", any()) }
    }
}