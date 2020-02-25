package cz.vutbr.fit.knot.enticing.management.shell

/**
 * Shell command to be executed by the underlying OS
 */
interface ShellCommand {
    /**
     * String version of the command the execute
     */
    val value: String
}

/**
 * Command executed locally
 */
interface LocalCommand : ShellCommand {
    infix fun and(other: LocalCommand): LocalCommand {
        return AndCommand(this, other)
    }
}

data class SimpleCommand(override val value: String) : LocalCommand

/**
 * two commands connected with '&&'
 */
data class AndCommand(val left: ShellCommand, val right: ShellCommand) : LocalCommand {
    override val value: String
        get() = "${left.value} && ${right.value}"
}

/**
 * Command executed via ssh
 */
data class SshCommand(
        val username: String,
        val server: String,
        val command: ShellCommand,
        val forcePseudoTerminal: Boolean = false
) : ShellCommand {
    override val value: String = "ssh ${if (forcePseudoTerminal) "-T " else ""}$username@$server ${command.value}"
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
}

data class StartScreenCommand(
        val screenName: String,
        val command: LocalCommand
) : ShellCommand {
    override val value: String = "screen -S $screenName -d -m ${command.value}"
}

data class KillScreenCommand(
        val screenName: String
) : ShellCommand {
    override val value: String = "screen -S $screenName -X quit"
}




