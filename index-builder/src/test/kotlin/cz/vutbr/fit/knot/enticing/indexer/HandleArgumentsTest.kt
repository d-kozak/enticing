package cz.vutbr.fit.knot.enticing.indexer

import cz.vutbr.fit.knot.enticing.index.config.dsl.IndexBuilderConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File


val DUMMY_LOAD_CONFIGURATION: (path: String) -> IndexBuilderConfig = {
    IndexBuilderConfig().apply {
        this.output = File("../data/indexed")
    }
}

class HandleArgumentsTest {

    @Test
    fun `At least one argument necessary the config location`() {
        assertThrows<IllegalArgumentException> {
            handleArguments(loadConfig = DUMMY_LOAD_CONFIGURATION)
        }
    }

    @Test
    fun `Loading of dsl requested based on the path in the first argument`() {
        var calledWith: String? = null
        val load: (String) -> IndexBuilderConfig = { path ->
            calledWith != null && throw IllegalStateException("Called with param is set, which suggest that load was called more than once")
            calledWith = path
            DUMMY_LOAD_CONFIGURATION(path)
        }
        handleArguments("foo/bar/baz/config.kts", loadConfig = load)

        assertThat(calledWith)
                .isNotNull()
                .isEqualTo("foo/bar/baz/config.kts")
    }

    @Test
    fun `Two arguments not allowed, ambiguity`() {
        assertThrows<IllegalArgumentException> {
            handleArguments("foo/config.kts", "input", loadConfig = DUMMY_LOAD_CONFIGURATION)
        }
    }

    @Test
    fun `Input and output in config updated based on arguments`() {
        val config = handleArguments("foo/bar/baz/config.kts", "one.mg4j", "two.mg4j", "../data/indexed", loadConfig = DUMMY_LOAD_CONFIGURATION)
        assertThat(config.input)
                .isEqualTo(listOf(File("one.mg4j"), File("two.mg4j")))
        assertThat(config.output)
                .isEqualTo(File("../data/indexed"))
    }
}