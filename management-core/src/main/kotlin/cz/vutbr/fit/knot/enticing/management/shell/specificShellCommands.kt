package cz.vutbr.fit.knot.enticing.management.shell

import cz.vutbr.fit.knot.enticing.dto.ComponentInfo
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.DeploymentConfiguration
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.management.model.Mg4jFile
import cz.vutbr.fit.knot.enticing.mx.ServerProbe


suspend fun ShellCommandExecutor.startComponent(component: ComponentInfo, deploymentConfiguration: DeploymentConfiguration): String = when (component.type) {
    ComponentType.INDEX_SERVER -> startIndexServer(component.serverAddress, deploymentConfiguration.repository, deploymentConfiguration.configurationScript, component.port)
    ComponentType.WEBSERVER -> startWebserver(component.serverAddress, deploymentConfiguration.repository, deploymentConfiguration.configurationScript, component.port)
    ComponentType.MANAGEMENT_SERVER -> startManagementService(component.serverAddress, deploymentConfiguration.repository, deploymentConfiguration.configurationScript, component.port)
    else -> error("Cannot start component of type ${component.type}")
}

suspend fun ShellCommandExecutor.killComponent(component: ComponentInfo): String = when (component.type) {
    ComponentType.INDEX_SERVER -> killIndexServer(component.serverAddress)
    ComponentType.WEBSERVER -> killWebserver(component.serverAddress)
    ComponentType.MANAGEMENT_SERVER -> killManagementService(component.serverAddress)
    else -> error("Cannot start component of type ${component.type}")
}

suspend fun ShellCommandExecutor.localBuild(buildId: String, enticingHome: String): String {
    return this.execute(SimpleCommand("$enticingHome/bin/local-build $buildId"), workingDirectory = enticingHome)
}

suspend fun ShellCommandExecutor.copyJars(server: String, localRepository: String, remoteRepository: String, username: String? = null) = this.execute(SimpleCommand("scp $localRepository/lib/*.jar ${username ?: this.username}@$server:$remoteRepository/lib"))

@Incomplete("specify ports...")
suspend fun ShellCommandExecutor.startWebserver(server: String, enticingHome: String, configFile: String, port: Int, username: String? = null) = this.execute(SshCommand(username
        ?: this.username, server,
        StartScreenCommand("enticing-webserver", SimpleCommand("$enticingHome/bin/webserver $configFile $server --server.port=$port"))))

suspend fun ShellCommandExecutor.killWebserver(server: String, username: String? = null) =
        this.execute(SshCommand(username
                ?: this.username, server, KillScreenCommand("enticing-webserver")), checkReturnCode = false)

suspend fun ShellCommandExecutor.startManagementService(server: String, enticingHome: String, configFile: String, port: Int, username: String? = null) = this.execute(SshCommand(username
        ?: this.username, server,
        StartScreenCommand("enticing-management", SimpleCommand("$enticingHome/bin/management-service $configFile $server --debug.runner.start=false --server.port=$port"))))

suspend fun ShellCommandExecutor.killManagementService(server: String, username: String? = null) =
        this.execute(SshCommand(username
                ?: this.username, server, KillScreenCommand("enticing-management")), checkReturnCode = false)

suspend fun ShellCommandExecutor.startIndexServer(server: String, enticingHome: String, configFile: String, port: Int, username: String? = null) = this.execute(SshCommand(username
        ?: this.username, server,
        StartScreenCommand("enticing-index-server", SimpleCommand("$enticingHome/bin/index-server $configFile $server --server.port=$port"))))

suspend fun ShellCommandExecutor.killIndexServer(server: String, username: String? = null) =
        this.execute(SshCommand(username
                ?: this.username, server, KillScreenCommand("enticing-index-server")), checkReturnCode = false)

suspend fun ShellCommandExecutor.preprocessCollections(server: String, enticingHome: String, configFile: String, username: String? = null) = this.execute(
        SshCommand(username ?: this.username, server,
//                SimpleCommand("screen -S index-builder $enticingHome/bin/index-builder $configFile $server"), forcePseudoTerminal = true), logPrefix = server)
                SimpleCommand("$enticingHome/bin/index-builder $configFile $server")), logPrefix = server)

suspend fun ShellCommandExecutor.probeServer(server: String, enticingHome: String, username: String? = null) = this.execute(
        SshCommand(username
                ?: this.username, server, SimpleCommand("$enticingHome/bin/server-probe"), forcePseudoTerminal = true)
).toDto<ServerProbe.Info>()

/**
 * connects to the given server, git pulls for new changes and builds the project
 */
suspend fun ShellCommandExecutor.pullAndBuild(sourceServer: String, repository: String, username: String? = null) {
    val local = SimpleCommand("cd $repository") and SimpleCommand("git pull") and SimpleCommand("gradle buildAll")
    this.execute(SshCommand(username ?: this.username, sourceServer, local))
}

/**
 * copies files from source server to the destination server using scp
 */
suspend fun ShellCommandExecutor.copyFiles(sourceServer: String, files: List<Mg4jFile>, destinationServer: String, destinationDirectory: String, username: String? = null) {
    this.execute(SshCommand(username
            ?: this.username, sourceServer, SimpleCommand("scp ${files.joinToString(" ") { it.path }} $username@$destinationServer:$destinationDirectory")))
}

/**
 * creates a directory on a specified server using mkdir -p
 */
suspend fun ShellCommandExecutor.createRemoteDirectory(server: String, path: String, username: String? = null) {
    this.execute(SshCommand(username ?: this.username, server, SimpleCommand("mkdir -p $path")))
}

private val whitespaceRegex = """\s+""".toRegex()

private val sizeColumn = 4
private val pathColumn = 8

/**
 * Load mg4j files located in a given directory
 */
suspend fun ShellCommandExecutor.loadMg4jFiles(server: String, directory: String, fileLimit: Int = Int.MAX_VALUE, username: String? = null): List<Mg4jFile> {
    val stdout = this.dumpMgj4Files(server, directory, fileLimit, username)
    return stdout.split("\n").mapNotNull {
        val line = it.split(whitespaceRegex)
        if (line.size < 9) return@mapNotNull null
        Mg4jFile(line[pathColumn], line[sizeColumn].toLongOrNull() ?: return@mapNotNull null)
    }
}


suspend fun ShellCommandExecutor.recursiveRemove(server: String, directory: String, username: String? = null) {
    this.execute(SshCommand(username
            ?: this.username, server, SimpleCommand("rm -rf $directory")), checkReturnCode = false)
}

suspend fun ShellCommandExecutor.loadFiles(server: String, directory: String, username: String? = null): List<String> {
    val stdout = this.execute(SshCommand(username
            ?: this.username, server, SimpleCommand("ls -l $directory")), printStdout = false, checkReturnCode = false)
    return stdout.split("\n").mapNotNull {
        val line = it.split(whitespaceRegex)
        if (line.size < 9) return@mapNotNull null
        line[pathColumn]
    }
}


/**
 * Dumps specified directory for mg4j files using ls
 */
private suspend fun ShellCommandExecutor.dumpMgj4Files(server: String, directory: String, fileLimit: Int, username: String? = null) =
        this.execute(
                SshCommand(username ?: this.username, server,
                        SimpleCommand("ls -l $directory/*.mg4j | head -n $fileLimit")), printStdout = false, checkReturnCode = false)