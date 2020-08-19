package cz.vutbr.fit.knot.enticing.management.shell

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.log.measure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.future.await
import java.io.File
import java.io.FileWriter
import java.io.InputStream

class ShellCommandExecutor(
        configuration: EnticingConfiguration,
        val scope: CoroutineScope,
        loggerFactory: LoggerFactory,
        logfile: String? = null
) : AutoCloseable {

    private val logger = loggerFactory.logger { }

    val username = configuration.authentication.username

    private val outputWriter = logfile?.let { FileWriter(it) }

    /**
     * @param checkReturnCode If true, 0 is required as return value
     * @param printStdout If true, stdout of the command will be printed to the console
     * @param printStderr If true, stderr of the command will be printed to the console
     */
    suspend fun execute(command: ShellCommand, workingDirectory: String? = null, logPrefix: String = "", checkReturnCode: Boolean = true, printStdout: Boolean = true, printStderr: Boolean = true): String = logger.measure("command", command.value) {
        outputWriter?.appendLine("Executing command $command")
        val builder = ProcessBuilder(listOf("bash", "-c", command.value))
        if (printStdout)
            builder.redirectOutput(ProcessBuilder.Redirect.PIPE)
        if (printStderr)
            builder.redirectError(ProcessBuilder.Redirect.PIPE)

        if (workingDirectory != null)
            builder.directory(File(workingDirectory))
        val process = builder.start()

        //  first read, then waitFor -> otherwise deadlock if the buffer gets full @see https://stackoverflow.com/questions/5483830/process-waitfor-never-returns
        val stdoutAsync = scope.async { consumeStream(process.inputStream, printStdout, logPrefix) }
        val stderrAsync = scope.async { consumeStream(process.errorStream, printStderr, logPrefix) }

        process.onExit().await()

        val stdout = stdoutAsync.await()
        val stderr = stderrAsync.await()

        // print again only if they were not printed already and the process failed (to provide diagnostic info)
        if (!printStdout && process.exitValue() != 0)
            println(stdout)

        if (!printStderr && process.exitValue() != 0)
            println(stderr)

        outputWriter?.appendLine("Process returned with exit value ${process.exitValue()}")
        check((!checkReturnCode || process.exitValue() == 0)) { "Command ${command.value} exited with value ${process.exitValue()}" }
        stdout
    }


    private fun consumeStream(stream: InputStream, printContent: Boolean, logPrefix: String): String = buildString {
        stream.bufferedReader().use {
            var line = it.readLine()
            while (line != null) {
                outputWriter?.appendLine(line)
                if (printContent) println(if (logPrefix.isNotBlank()) "$logPrefix: $line" else line)
                appendLine(line)
                line = it.readLine()
            }
        }
    }

    override fun close() {
        outputWriter?.close()
    }
}