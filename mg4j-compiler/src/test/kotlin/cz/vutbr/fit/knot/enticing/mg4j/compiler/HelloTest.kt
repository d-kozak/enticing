package cz.vutbr.fit.knot.enticing.mg4j.compiler

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class HelloTest {

    @Test
    fun `Hello test`() {
        assertThat(hello()).isEqualTo("hello")
    }
}