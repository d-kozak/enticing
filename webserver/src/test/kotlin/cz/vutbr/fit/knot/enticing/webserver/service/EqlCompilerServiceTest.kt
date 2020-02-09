package cz.vutbr.fit.knot.enticing.webserver.service


import cz.vutbr.fit.knot.enticing.dto.CorpusFormat
import cz.vutbr.fit.knot.enticing.dto.PureMgj4Node
import cz.vutbr.fit.knot.enticing.dto.toMetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.eql.compiler.dto.ParsedQuery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test


class EqlCompilerServiceTest {

    private val parseMock = mockk<EqlCompiler>()

    @Test
    fun `parseQuery calls eql parser`() {
        val service = EqlCompilerService(parseMock)
        every { parseMock.parseAndAnalyzeQuery("foo", any()) } returns ParsedQuery(PureMgj4Node("foo"))

        service.validateQuery("foo", CorpusFormat("dummy", emptyMap(), emptyMap()).toMetadataConfiguration())

        verify(exactly = 1) { parseMock.parseAndAnalyzeQuery("foo", any()) }
    }
}