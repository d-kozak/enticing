package cz.vutbr.fit.knot.enticing.management.shell

import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.log.logger
import kotlinx.coroutines.future.await
import java.util.concurrent.TimeUnit

class ShellCommandExecutor(logService: MeasuringLogService) {

    private val logger = logService.logger { }

    /**
     * @param checkReturnCode If true, 0 is required as return value
     * @param printStdout If true, stdout of the command will be printed to the console
     * @param printStderr If true, stderr of the command will be printed to the console
     */
    suspend fun execute(command: ShellCommand, checkReturnCode: Boolean = true, printStdout: Boolean = true, printStderr: Boolean = true, timeout: Long = 30, timeoutUnit: TimeUnit = TimeUnit.SECONDS): String = logger.measure("command ${command.value}") {
        val builder = ProcessBuilder(command.value.split(" "))
        if (printStdout)
            builder.redirectOutput(ProcessBuilder.Redirect.PIPE)
        if (printStderr)
            builder.redirectError(ProcessBuilder.Redirect.PIPE)

        val process = builder.start()

        //  first read, then waitFor -> otherwise deadlock if the buffer gets full @see https://stackoverflow.com/questions/5483830/process-waitfor-never-returns
        val stdout = process.inputStream.bufferedReader().readText()
        val stderr = process.errorStream.bufferedReader().readText()

        process.onExit().await()

        if (printStdout || process.exitValue() != 0)
            println(stdout)

        if (printStderr || process.exitValue() != 0)
            println(stderr)

        check((!checkReturnCode || process.exitValue() == 0)) { "Command ${command.value} exited with value ${process.exitValue()}" }
        stdout
    }

}