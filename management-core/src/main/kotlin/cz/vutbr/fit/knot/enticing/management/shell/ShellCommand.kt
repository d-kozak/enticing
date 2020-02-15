package cz.vutbr.fit.knot.enticing.management.shell

/**
 * Shell command to be executed by the underlying OS
 */
interface ShellCommand {
    /**
     * String version of the command the execute
     */
    val value: String

    fun split(): List<String> = listOf(value)

}

/**
 * Command executed locally
 */
data class LocalCommand(override val value: String) : ShellCommand {
    override fun split(): List<String> = value.split(" ")
}

/**
 * Command executed via ssh
 */
data class SshCommand(
        val username: String,
        val server: String,
        val command: ShellCommand
) : ShellCommand {
    override val value: String = "ssh $username@$server ${command.value}"

    override fun split(): List<String> = listOf("ssh", "$username@$server", command.value)
}

/**
 * Command executed on multiple machines via parallel ssh
 */
data class ParallelSshCommand(
        val username: String,
        val servers: List<String>,
        val command: ShellCommand
) : ShellCommand {
    override val value: String = "parallel-ssh -l $username ${servers.map { "-H $it" }.joinToString(" ")} -i ${command.value}"

    override fun split(): List<String> {
        val res = mutableListOf("parallel-ssh", "-l", username)

        for (server in servers) {
            res.add("-H")
            res.add(server)
        }
        res.add("-i")
        res.add(command.value)
        return res
    }
}

data class StartScreenCommand(
        val screenName: String,
        val logFile: String,
        val command: LocalCommand
) : ShellCommand {
    override val value: String = "screen -S $screenName -d -m ${command.value} && screen -S $screenName -X logfile $logFile && screen -S $screenName -X log"
}

data class KillScreenCommand(
        val screenName: String
) : ShellCommand {
    override val value: String = "screen -S $screenName -X quit"
}




