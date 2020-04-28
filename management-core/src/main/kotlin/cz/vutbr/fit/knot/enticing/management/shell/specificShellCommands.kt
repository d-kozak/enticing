package cz.vutbr.fit.knot.enticing.management.shell

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.management.model.Mg4jFile
import cz.vutbr.fit.knot.enticing.mx.ServerProbe

suspend fun ShellCommandExecutor.localBuild(enticingHome: String): String {
    @Incomplete("find a way to just use 'gradle'")
    val cmd = "/home/dkozak/.sdkman/candidates/gradle/current/bin/gradle buildAll"
    return this.execute(SimpleCommand(cmd), workingDirectory = enticingHome)
}

suspend fun ShellCommandExecutor.localTest(enticingHome: String): String {
    @Incomplete("find a way to just use 'gradle'")
    val cmd = "/home/dkozak/.sdkman/candidates/gradle/current/bin/gradle clean test --info"
    return this.execute(SimpleCommand(cmd), workingDirectory = enticingHome)
}

suspend fun ShellCommandExecutor.copyJars(username: String, server: String, localRepository: String, remoteRepository: String) = this.execute(SimpleCommand("scp $localRepository/lib/*.jar $username@$server:$remoteRepository/lib"))

@Incomplete("specify ports...")
suspend fun ShellCommandExecutor.startWebserver(username: String, server: String, enticingHome: String, configFile: String, port: Int) = this.execute(SshCommand(username, server,
        StartScreenCommand("enticing-webserver", SimpleCommand("$enticingHome/bin/webserver $configFile $server --server.port=$port"))))

suspend fun ShellCommandExecutor.killWebserver(username: String, server: String) =
        this.execute(SshCommand(username, server, KillScreenCommand("enticing-webserver")), checkReturnCode = false)

suspend fun ShellCommandExecutor.startManagementService(username: String, server: String, enticingHome: String, configFile: String, port: Int) = this.execute(SshCommand(username, server,
        StartScreenCommand("enticing-management", SimpleCommand("$enticingHome/bin/management-service $configFile $server --debug.runner.start=false --server.port=$port"))))

suspend fun ShellCommandExecutor.killManagementService(username: String, server: String) =
        this.execute(SshCommand(username, server, KillScreenCommand("enticing-management")), checkReturnCode = false)

suspend fun ShellCommandExecutor.startIndexServer(username: String, server: String, enticingHome: String, configFile: String, port: Int) = this.execute(SshCommand(username, server,
        StartScreenCommand("enticing-index-server", SimpleCommand("$enticingHome/bin/index-server $configFile $server --server.port=$port"))))

suspend fun ShellCommandExecutor.killIndexServer(username: String, server: String) =
        this.execute(SshCommand(username, server, KillScreenCommand("enticing-index-server")), checkReturnCode = false)

suspend fun ShellCommandExecutor.preprocessCollections(username: String, server: String, enticingHome: String, configFile: String) = this.execute(
        SshCommand(username, server,
//                SimpleCommand("screen -S index-builder $enticingHome/bin/index-builder $configFile $server"), forcePseudoTerminal = true), logPrefix = server)
                SimpleCommand("$enticingHome/bin/index-builder $configFile $server")), logPrefix = server)

suspend fun ShellCommandExecutor.probeServer(username: String, server: String, enticingHome: String) = this.execute(
        SshCommand(username, server, SimpleCommand("$enticingHome/bin/server-probe"), forcePseudoTerminal = true)
).toDto<ServerProbe.Info>()

/**
 * connects to the given server, git pulls for new changes and builds the project
 */
suspend fun ShellCommandExecutor.pullAndBuild(username: String, sourceServer: String, repository: String) {
    val local = SimpleCommand("cd $repository") and SimpleCommand("git pull") and SimpleCommand("gradle buildAll")
    this.execute(SshCommand(username, sourceServer, local))
}

/**
 * copies files from source server to the destination server using scp
 */
suspend fun ShellCommandExecutor.copyFiles(username: String, sourceServer: String, files: List<Mg4jFile>, destinationServer: String, destinationDirectory: String) {
    this.execute(SshCommand(username, sourceServer, SimpleCommand("scp ${files.joinToString(" ") { it.path }} $username@$destinationServer:$destinationDirectory")))
}

/**
 * creates a directory on a specified server using mkdir -p
 */
suspend fun ShellCommandExecutor.createRemoteDirectory(username: String, server: String, path: String) {
    this.execute(SshCommand(username, server, SimpleCommand("mkdir -p $path")))
}

private val whitespaceRegex = """\s+""".toRegex()

private val sizeColumn = 4
private val pathColumn = 8

/**
 * Load mg4j files located in a given directory
 */
suspend fun ShellCommandExecutor.loadMg4jFiles(username: String, server: String, directory: String, fileLimit: Int = Int.MAX_VALUE): List<Mg4jFile> {
    val stdout = this.dumpMgj4Files(username, server, directory, fileLimit)
    return stdout.split("\n").mapNotNull {
        val line = it.split(whitespaceRegex)
        if (line.size < 9) return@mapNotNull null
        Mg4jFile(line[pathColumn], line[sizeColumn].toLongOrNull() ?: return@mapNotNull null)
    }
}


suspend fun ShellCommandExecutor.recursiveRemove(username: String, server: String, directory: String) {
    this.execute(SshCommand(username, server, SimpleCommand("rm -rf $directory")), checkReturnCode = false)
}

suspend fun ShellCommandExecutor.loadFiles(username: String, server: String, directory: String): List<String> {
    val stdout = this.execute(SshCommand(username, server, SimpleCommand("ls -l $directory")), printStdout = false, checkReturnCode = false)
    return stdout.split("\n").mapNotNull {
        val line = it.split(whitespaceRegex)
        if (line.size < 9) return@mapNotNull null
        line[pathColumn]
    }
}


/**
 * Dumps specified directory for mg4j files using ls
 */
private suspend fun ShellCommandExecutor.dumpMgj4Files(username: String, server: String, directory: String, fileLimit: Int) =
        this.execute(
                SshCommand(username, server,
                        SimpleCommand("ls -l $directory/*.mg4j | head -n $fileLimit")), printStdout = false, checkReturnCode = false)