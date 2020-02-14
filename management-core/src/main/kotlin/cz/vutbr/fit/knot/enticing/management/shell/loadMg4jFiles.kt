package cz.vutbr.fit.knot.enticing.management.shell

import cz.vutbr.fit.knot.enticing.management.model.Mg4jFile

fun PrintMg4jFilesCommand(username: String, server: String, directory: String) = SshCommand(username, server, LocalCommand("ls -l $directory/*.mg4j"))

private val whitespaceRegex = """\s+""".toRegex()

private val sizeColumn = 4
private val pathColumn = 8

/**
 * Load mg4j files located in a given directory
 */
fun ShellCommandExecutor.loadMg4jFiles(username: String, server: String, directory: String): List<Mg4jFile> {
    val stdout = this.execute(PrintMg4jFilesCommand(username, server, directory), printStdout = false)
    return stdout.split("\n").mapNotNull {
        val line = it.split(whitespaceRegex)
        if (line.size < 9) return@mapNotNull null
        Mg4jFile(line[pathColumn], line[sizeColumn].toLongOrNull() ?: return@mapNotNull null)
    }
}


