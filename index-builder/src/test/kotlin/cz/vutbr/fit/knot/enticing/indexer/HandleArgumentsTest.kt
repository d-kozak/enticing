package cz.vutbr.fit.knot.enticing.indexer

import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.indexBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File


val DUMMY_LOAD_CONFIGURATION: (path: String) -> IndexBuilderConfig = {
    indexBuilder {

    }
}

class HandleArgumentsTest {

    @Test
    fun `At least three arguments necessary`() {
        assertThrows<IllegalArgumentException> {
            handleArguments(loadConfig = DUMMY_LOAD_CONFIGURATION)
        }
        assertThrows<IllegalArgumentException> {
            handleArguments("foo/config.kts", loadConfig = DUMMY_LOAD_CONFIGURATION)
        }
        assertThrows<IllegalArgumentException> {
            handleArguments("foo/config.kts", "../data/mg4j", loadConfig = DUMMY_LOAD_CONFIGURATION)
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
        handleArguments("foo/bar/baz/config.kts", "../data/mg4j", "../data/indexed", loadConfig = load)

        assertThat(calledWith)
                .isNotNull()
                .isEqualTo("foo/bar/baz/config.kts")
    }

    @Test
    fun `Exactly specified input mgj4 files`() {
        val config = handleArguments("foo/bar/baz/config.kts", "../data/mg4j/cc1.mg4j", "../data/mg4j/cc2.mg4j", "../data/indexed", loadConfig = DUMMY_LOAD_CONFIGURATION)
        assertThat(config.input.toSet())
                .isEqualTo(setOf(File("../data/mg4j/cc1.mg4j"), File("../data/mg4j/cc2.mg4j")))
        assertThat(config.output)
                .isEqualTo(File("../data/indexed"))
    }

    @Test
    fun `Input specified as directory`() {
        val config = handleArguments("foo/bar/baz/config.kts", "../data/mg4j", "../data/indexed", loadConfig = DUMMY_LOAD_CONFIGURATION)
        assertThat(config.input.toSet())
                .isEqualTo(setOf(File("../data/mg4j/cc1.mg4j"), File("../data/mg4j/cc2.mg4j"), File("../data/mg4j/cc3.mg4j"), File("../data/mg4j/small.mg4j")))
        assertThat(config.output)
                .isEqualTo(File("../data/indexed"))
    }
}