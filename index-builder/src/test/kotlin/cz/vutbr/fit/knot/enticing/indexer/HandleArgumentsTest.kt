package cz.vutbr.fit.knot.enticing.indexer

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class HandleArgumentsTest {

    @Test
    fun `At least four arguments necessary`() {
        assertThrows<IllegalArgumentException> {
            handleArguments()
        }
        assertThrows<IllegalArgumentException> {
            handleArguments("foo/config.kts")
        }
        assertThrows<IllegalArgumentException> {
            handleArguments("foo/config.kts", "../data/mg4j")
        }
        assertThrows<IllegalArgumentException> {
            handleArguments("foo/config.kts", "../data/mg4j", "../data/indexed")
        }
    }

    @Test
    fun `Loading of dsl requested based on the path in the first argument`() {
        var calledWith: String? = null
        val load: (String) -> EnticingConfiguration = { path ->
            calledWith != null && throw IllegalStateException("Called with param is set, which suggest that load was called more than once")
            calledWith = path
            fullConf
        }
        handleArguments("foo/bar/baz/config.kts", "col1") { load(it) }

        assertThat(calledWith)
                .isNotNull()
                .isEqualTo("foo/bar/baz/config.kts")
    }

}