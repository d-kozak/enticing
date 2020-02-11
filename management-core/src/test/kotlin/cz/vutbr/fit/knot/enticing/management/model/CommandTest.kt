package cz.vutbr.fit.knot.enticing.management.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CommandTest {

    @Nested
    inner class LocalCommandTest {

        @Test
        fun `simple echo`() {
            val cmd = LocalCommand("echo hello")
            assertThat(cmd.execute())
                    .isEqualTo("hello\n")
        }
    }


    @Nested
    @Disabled // these tests can be performed only from within knot network
    inner class SshTest {
        @Test
        fun `simple echo`() {
            val cmd = SshCommand("xkozak15", "minerva3.fit.vutbr.cz", LocalCommand("echo hello"))
            assertThat(cmd.execute())
                    .isEqualTo("hello\n")
        }

        @Test
        fun `parallel echo`() {
            val cmd = ParallelSshCommand("xkozak15", listOf("minerva1.fit.vutbr.cz", "minerva2.fit.vutbr.cz", "minerva3.fit.vutbr.cz"), LocalCommand("echo hello"))
            assertThat(cmd.execute())
                    .isNotEmpty()
        }
    }

    @Nested
    inner class ToStringTest {

        @Test
        fun `ssh command`() {
            val cmd = SshCommand("xkozak15", "localhost", LocalCommand("ls -l"))
            assertThat(cmd.value)
                    .isEqualTo("ssh xkozak15@localhost ls -l")
        }

        @Test
        fun `parallel ssh test`() {
            val cmd = ParallelSshCommand(
                    "xkozak15"
                    , listOf("123", "456", "789"),
                    LocalCommand("rm foo")
            )
            assertThat(cmd.value)
                    .isEqualTo("parallel-ssh -l xkozak15 -H 123 -H 456 -H 789 -i rm foo")
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
                    "xkozak15"
                    , listOf("123", "456", "789"),
                    StartScreenCommand("screen1", "foo.log", LocalCommand("run-run-long"))
            )
            assertThat(cmd.value).isEqualTo("parallel-ssh -l xkozak15 -H 123 -H 456 -H 789 -i screen -S screen1 -d -m run-run-long && screen -S screen1 -X logfile foo.log && screen -S screen1 -X log")
        }
    }
}