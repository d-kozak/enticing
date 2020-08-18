package cz.vutbr.fit.knot.enticing.management.shell

import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.management.config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ShellCommandTest {

    val executor = ShellCommandExecutor(config, CoroutineScope(Dispatchers.IO), SimpleStdoutLoggerFactory)

    @Nested
    inner class LocalCommandTest {

        @Test
        fun `simple echo`() = runBlocking<Unit> {
            val cmd = SimpleCommand("echo hello")
            assertThat(executor.execute(cmd))
                    .isEqualTo("hello\n")
        }
    }

    @Nested
    @Disabled // these tests can be performed only from within knot network
    inner class SshTest {
        @Test
        fun `simple echo`() = runBlocking<Unit> {
            val cmd = SshCommand("xkozak15", "minerva3.fit.vutbr.cz", SimpleCommand("echo hello"))
            assertThat(executor.execute(cmd))
                    .isEqualTo("hello\n")
        }


        @Test
        fun `parallel echo`() = runBlocking<Unit> {
            val cmd = ParallelSshCommand("xkozak15", listOf("minerva1.fit.vutbr.cz", "minerva2.fit.vutbr.cz", "minerva3.fit.vutbr.cz"), SimpleCommand("echo hello"))
            assertThat(executor.execute(cmd))
                    .isNotEmpty()
        }


        @Test
        fun `ls corpus dir`() = runBlocking {
            val cmd = SshCommand("xkozak15", "minerva3.fit.vutbr.cz", SimpleCommand("ls -l /var/xdolez52/Zpracovani_Wikipedie/html_from_wikipedia_en_all_novid_2018-10.zim/6-mg4j/old-2019-10-18/*.mg4j"))
            println(executor.execute(cmd))
        }

    }

    @Nested
    inner class ToStringTest {

        @Test
        fun `ssh command`() {
            val cmd = SshCommand("xkozak15", "Simplehost", SimpleCommand("ls -l"))
            assertThat(cmd.value)
                    .isEqualTo("ssh xkozak15@Simplehost 'ls -l'")
        }

        @Test
        fun `parallel ssh test`() {
            val cmd = ParallelSshCommand(
                    "xkozak15", listOf("123", "456", "789"),
                    SimpleCommand("rm foo")
            )
            assertThat(cmd.value)
                    .isEqualTo("parallel-ssh -l xkozak15 -H 123 -H 456 -H 789 -i rm foo")
        }

        @Test
        fun `start screen test`() {
            val cmd = StartScreenCommand("screen1", SimpleCommand("run-run-long"))
            assertThat(cmd.value)
                    .isEqualTo("screen -S screen1 -d -m run-run-long")
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
                    "xkozak15", listOf("123", "456", "789"),
                    StartScreenCommand("screen1", SimpleCommand("run-run-long"))
            )
            assertThat(cmd.value).isEqualTo("parallel-ssh -l xkozak15 -H 123 -H 456 -H 789 -i screen -S screen1 -d -m run-run-long")
        }
    }

    @Nested
    inner class DumpMg4jFilesTest {

        @Test
        fun `dump new wiki limited`() = runBlocking {
            // ssh xkozak15@minerva3.fit.vutbr.cz ls -l /var/xdolez52/Zpracovani_Wikipedie/html_from_wikipedia_en_all_nopic_2019-10.zim/6-mg4j/old-2020-01-03/*.mg4j | head -n 50
            val files = executor.loadMg4jFiles("minerva3.fit.vutbr.cz",
                    "/var/xdolez52/Zpracovani_Wikipedie/html_from_wikipedia_en_all_nopic_2019-10.zim/6-mg4j/old-2020-01-03", 50, "xkozak15")
            println(files)
        }
    }
}