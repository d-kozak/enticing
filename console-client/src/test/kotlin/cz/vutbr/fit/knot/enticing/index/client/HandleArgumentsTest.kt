package cz.vutbr.fit.knot.enticing.index.client

import cz.vutbr.fit.knot.enticing.dto.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.TextFormat
import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.io.FileNotFoundException


class HandleArgumentsTest {

    @Test
    fun `just config file`() {
        val config = handleArguments(arrayOf("src/test/resources/client.config.local.kts"))
        assertThat(config.clientType).isInstanceOf(ConsoleClientType.LocalIndex::class.java)
    }

    @Test
    fun `config file and query`() {
        val query = "hello darkness, my old friend"
        val config = handleArguments(arrayOf("src/test/resources/client.config.local.kts", query))
        assertThat(config.clientType).isInstanceOf(ConsoleClientType.LocalIndex::class.java)
        assertThat(config.searchConfig.query).isEqualTo(query)
    }

    @Test
    fun `text format html anhd config file`() {
        val config = handleArguments(arrayOf("-t", "HTML", "src/test/resources/client.config.local.kts"))
        assertThat(config.clientType).isInstanceOf(ConsoleClientType.LocalIndex::class.java)
        assertThat(config.searchConfig.textFormat).isEqualTo(TextFormat.HTML)
    }

    @Test
    fun `text format plain and config file`() {
        val config = handleArguments(arrayOf("-t", "PLAIN_TEXT", "src/test/resources/client.config.local.kts"))
        assertThat(config.clientType).isInstanceOf(ConsoleClientType.LocalIndex::class.java)
        assertThat(config.searchConfig.textFormat).isEqualTo(TextFormat.PLAIN_TEXT)
    }

    @Test
    fun `result format IDENTIFIER_LIST and config file`() {
        val config = handleArguments(arrayOf("-r", "IDENTIFIER_LIST", "src/test/resources/client.config.local.kts"))
        assertThat(config.clientType).isInstanceOf(ConsoleClientType.LocalIndex::class.java)
        assertThat(config.searchConfig.resultFormat).isEqualTo(ResultFormat.IDENTIFIER_LIST)
    }

    @Test
    fun `result format text format and output file`() {
        val config = handleArguments(arrayOf("-r", "IDENTIFIER_LIST", "-t", "PLAIN_TEXT", "-f", "out", "src/test/resources/client.config.local.kts"))
        assertThat(config.clientType).isInstanceOf(ConsoleClientType.LocalIndex::class.java)
        assertThat(config.searchConfig.resultFormat).isEqualTo(ResultFormat.IDENTIFIER_LIST)
        assertThat(config.searchConfig.textFormat).isEqualTo(TextFormat.PLAIN_TEXT)
        assertThat(config.searchConfig.outputFile).isEqualTo(File("out"))
    }

    @Test
    fun `result format text format and output file and query`() {
        val config = handleArguments(arrayOf("-r", "IDENTIFIER_LIST", "-t", "PLAIN_TEXT", "-f", "out", "src/test/resources/client.config.local.kts", "foo"))
        assertThat(config.clientType).isInstanceOf(ConsoleClientType.LocalIndex::class.java)
        assertThat(config.searchConfig.resultFormat).isEqualTo(ResultFormat.IDENTIFIER_LIST)
        assertThat(config.searchConfig.textFormat).isEqualTo(TextFormat.PLAIN_TEXT)
        assertThat(config.searchConfig.outputFile).isEqualTo(File("out"))
        assertThat(config.searchConfig.query).isEqualTo("foo")
    }

    @Test
    fun `missing result type`() {
        assertThrows<IllegalArgumentException> { handleArguments(arrayOf("-r", "-t", "PLAIN_TEXT", "-f", "out", "src/test/resources/client.config.local.kts", "foo")) }
    }

    @Test
    fun `missing text type`() {
        assertThrows<IllegalArgumentException> { handleArguments(arrayOf("-r", "IDENTIFIER_LIST", "-t", "-f", "out", "src/test/resources/client.config.local.kts", "foo")) }
    }

    @Test
    fun `missing config file`() {
        assertThrows<FileNotFoundException> { handleArguments(arrayOf("-r", "IDENTIFIER_LIST", "-t", "PLAIN_TEXT", "-f", "out", "foo")) }
    }

}