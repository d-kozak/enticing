package cz.vutbr.fit.knot.enticing.dto.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LoaderTest {

    @Test
    fun `Load from file`() {
        val fib42: Int = executeScript("src/test/resources/fib.kts")
        assertThat(fib42)
                .isEqualTo(267914296)
    }
}