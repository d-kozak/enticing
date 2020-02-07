package cz.vutbr.fit.knot.enticing.management.model

import java.util.concurrent.TimeUnit

interface Command {
    /**
     * String version of the command the execute
     */
    val value: String

    /**
     * @param checkReturnCode If true, 0 is required as return value
     * @param printStdout If true, stdout of the command will be printed to the console
     * @param printStderr If true, stderr of the command will be printed to the console
     */
    fun execute(checkReturnCode: Boolean = true, printStdout: Boolean = true, printStderr: Boolean = true): String {
        val builder = ProcessBuilder(value.split(" "))
        if (printStdout)
            builder.redirectOutput(ProcessBuilder.Redirect.PIPE)
        if (printStderr)
            builder.redirectError(ProcessBuilder.Redirect.PIPE)

        val process = builder.start()
        process.waitFor(3, TimeUnit.MINUTES)
        if (checkReturnCode)
            check(process.exitValue() == 0) { "Command $this exited with value ${process.exitValue()}" }
        return process.inputStream.bufferedReader().readText()
    }
}

/**
 * Command executed locally
 */
data class LocalCommand(override val value: String) : Command

/**
 * Command executed via ssh
 */
data class SshCommand(
        val username: String,
        val server: String,
        val command: Command
) : Command {
    override val value: String = "ssh $username@$server '${command.value}'"
}

/**
 * Command executed on multiple machines via parallel ssh
 */
data class ParallelSshCommand(
        val servers: List<String>,
        val username: String,
        val command: Command
) : Command {
    override val value: String = "parallel-ssh -l $username -H '${servers.joinToString(" ")}' -i '${command.value}'"
}

data class StartScreenCommand(
        val screenName: String,
        val logFile: String,
        val command: LocalCommand
) : Command {
    override val value: String = "screen -S $screenName -d -m ${command.value} && screen -S $screenName -X logfile $logFile && screen -S $screenName -X log"
}

data class KillScreenCommand(
        val screenName: String
) : Command {
    override val value: String = "screen -S $screenName -X quit"
}




