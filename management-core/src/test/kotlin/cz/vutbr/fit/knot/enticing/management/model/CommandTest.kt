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

    @Nested
    inner class ToStringTest {

        @Test
        fun `ssh command`() {
            val cmd = SshCommand("xkozak15", "localhost", LocalCommand("ls -l"))
            assertThat(cmd.value)
                    .isEqualTo("ssh xkozak15@localhost 'ls -l'")
        }

        @Test
        fun `parallel ssh test`() {
            val cmd = ParallelSshCommand(
                    listOf("123", "456", "789")
                    , "xkozak15",
                    LocalCommand("rm foo")
            )
            assertThat(cmd.value)
                    .isEqualTo("parallel-ssh -l xkozak15 -H '123 456 789' -i 'rm foo'")
        }

        @Test
        fun `start screen test`() {
            val cmd = StartScreenCommand("screen1", "foo.log", LocalCommand("run-run-long"))
            assertThat(cmd.value)
                    .isEqualTo("screen -S screen1 -d -m run-run-long && screen -S screen1 -X logfile foo.log && screen -S screen1 -X log")
        }

        @Test
        fun `kill screen command`() {
            val cmd = KillScreenCommand("baz")
            assertThat(cmd.value)
                    .isEqualTo("screen -S baz -X quit")
        }

        @Test
        fun `parallel screen`() {
            val cmd = ParallelSshCommand(
                    listOf("123", "456", "789")
                    , "xkozak15",
                    StartScreenCommand("screen1", "foo.log", LocalCommand("run-run-long"))
            )
            assertThat(cmd.value).isEqualTo("parallel-ssh -l xkozak15 -H '123 456 789' -i 'screen -S screen1 -d -m run-run-long && screen -S screen1 -X logfile foo.log && screen -S screen1 -X log'")
        }
    }
}