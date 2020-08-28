package cz.vutbr.fit.knot.enticing.management

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


internal class ManagementCliArgumentsTest {

    @Test
    fun `just config script`() {
        val conf = parseCliArgs(arrayOf("release", "../dto/src/test/resources/config.kts"))
                .validateOrFail()
        assertThat(conf.toString()).isEqualTo("ManagementCliArguments(scriptPath='../dto/src/test/resources/config.kts',corpuses=[wiki-2018])")
        assertThat(conf.webserver).isFalse()
        assertThat(conf.kill).isFalse()
        assertThat(conf.indexServers).isFalse()
    }

    @Test
    fun `kill webserver`() {
        val conf = parseCliArgs(arrayOf("release", "../dto/src/test/resources/config.kts", "-kw"))
                .validateOrFail()
        assertThat(conf.webserver).isTrue()
        assertThat(conf.kill).isTrue()
        assertThat(conf.indexServers).isFalse()
    }

    @Test
    fun `specified corpuses`() {
        val conf = parseCliArgs(arrayOf("release", "../dto/src/test/resources/config.kts", "-c", "wiki", "-c", "CC"))
                .validateOrFail()
        assertThat(conf.corpuses)
                .contains("wiki", "CC")
    }
}