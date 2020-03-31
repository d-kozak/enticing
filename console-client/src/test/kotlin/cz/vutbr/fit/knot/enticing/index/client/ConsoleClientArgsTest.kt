package cz.vutbr.fit.knot.enticing.index.client

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class ConsoleClientArgsTest {

    @Test
    fun `single query webserver stdout`() {
        val args = parseCliArgs("-q ahoj -w athena10.fit.vutbr.cz:8080".asArgs())
        assertThat(args.query).isEqualTo("ahoj")
        assertThat(args.webserver).isEqualTo("athena10.fit.vutbr.cz:8080")
    }
}