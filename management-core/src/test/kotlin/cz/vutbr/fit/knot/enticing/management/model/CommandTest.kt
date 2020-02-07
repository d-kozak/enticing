package cz.vutbr.fit.knot.enticing.management.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CommandTest {

    @Nested
    inner class LocalCommandTest {

        @Test
        fun `simple ls`() {
            val cmd = LocalCommand("echo hello")
            assertThat(cmd.execute())
                    .isEqualTo("hello\n")
        }
    }
}