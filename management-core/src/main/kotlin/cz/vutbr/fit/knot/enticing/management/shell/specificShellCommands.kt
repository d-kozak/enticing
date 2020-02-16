package cz.vutbr.fit.knot.enticing.management.shell

import cz.vutbr.fit.knot.enticing.management.model.Mg4jFile


/**
 * copies files from source server to the destination server using scp
 */
suspend fun ShellCommandExecutor.copyFiles(username: String, sourceServer: String, files: List<Mg4jFile>, destinationServer: String, destinationDirectory: String) {
    this.execute(SshCommand(username, sourceServer, LocalCommand("scp ${files.joinToString(" ") { it.path }} $username@$destinationServer:$destinationDirectory")))
}

/**
 * creates a directory on a specified server using mkdir -p
 */
suspend fun ShellCommandExecutor.createRemoteDirectory(username: String, server: String, path: String) {
    this.execute(SshCommand(username, server, LocalCommand("mkdir -p $path")))
}

private val whitespaceRegex = """\s+""".toRegex()

private val sizeColumn = 4
private val pathColumn = 8

/**
 * Load mg4j files located in a given directory
 */
suspend fun ShellCommandExecutor.loadMg4jFiles(username: String, server: String, directory: String): List<Mg4jFile> {
    val stdout = this.dumpMgj4Files(username, server, directory)
    return stdout.split("\n").mapNotNull {
        val line = it.split(whitespaceRegex)
        if (line.size < 9) return@mapNotNull null
        Mg4jFile(line[pathColumn], line[sizeColumn].toLongOrNull() ?: return@mapNotNull null)
    }
}


suspend fun ShellCommandExecutor.recursiveRemove(username: String, server: String, directory: String) {
    this.execute(SshCommand(username, server, LocalCommand("rm -rf $directory")), checkReturnCode = false)
}

suspend fun ShellCommandExecutor.loadFiles(username: String, server: String, directory: String): List<String> {
    val stdout = this.execute(SshCommand(username, server, LocalCommand("ls -l $directory")), printStdout = false, checkReturnCode = false)
    return stdout.split("\n").mapNotNull {
        val line = it.split(whitespaceRegex)
        if (line.size < 9) return@mapNotNull null
        line[pathColumn]
    }
}


/**
 * Dumps specified directory for mg4j files using ls
 */
private suspend fun ShellCommandExecutor.dumpMgj4Files(username: String, server: String, directory: String) = this.execute(SshCommand(username, server, LocalCommand("ls -l $directory/*.mg4j")), printStdout = false, checkReturnCode = false)