package cz.vutbr.fit.knot.enticing.indexer

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class HandleArgumentsTest {

    @Test
    fun `Exactly arguments necessary`() {
        assertThrows<IllegalArgumentException> {
            handleArguments()
        }
        assertThrows<IllegalArgumentException> {
            handleArguments("../dto/src/test/resources/config.kts")
        }
        assertThrows<IllegalArgumentException> {
            handleArguments("../dto/src/test/resources/config.kts", "foo")
        }
    }

    @Test
    fun `Loading of dsl requested based on the path in the first argument`() {
        var calledWith: String? = null
        val load: (String) -> EnticingConfiguration = { path ->
            calledWith != null && throw IllegalStateException("Called with param is set, which suggest that load was called more than once")
            calledWith = path
            executeScript(path)
        }

        handleArguments("../dto/src/test/resources/config.kts", "minerva3.fit.vutbr.cz") { load(it) }

        assertThat(calledWith)
                .isNotNull()
                .isEqualTo("../dto/src/test/resources/config.kts")
    }

}