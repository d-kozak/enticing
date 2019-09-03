package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.dto.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.TextFormat
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientType
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import java.io.File

fun handleArguments(args: Array<String>): ConsoleClientConfig {
    var resultFormat: ResultFormat? = null
    var textFormat: TextFormat? = null
    var outputFile: File? = null

    var i = 0
    while (i < args.size && args[i].startsWith("-")) {
        check(i != args.size - 1) { "Option ${args[i]} should be followed by a value" }
        when (args[i]) {
            "-f" -> outputFile = File(args[i + 1])
            "-r" -> resultFormat = ResultFormat.valueOf(args[i + 1])
            "-t" -> textFormat = TextFormat.valueOf(args[i + 1])
        }
        i += 2
    }
    check(i < args.size) { "Config file path expected" }
    val config = executeScript<ConsoleClientConfig>(args[i++])

    if (i < args.size) {
        config.searchConfig.query = args[i++]
    }

    check(i == args.size) { "No more args expected" }

    if (resultFormat != null) {
        config.searchConfig.resultFormat = resultFormat
    }

    if (textFormat != null) {
        config.searchConfig.textFormat = textFormat
    }

    if (outputFile != null) {
        config.searchConfig.outputFile = outputFile
    }

    val errors = config.validate()
    if (errors.isNotEmpty()) {
        throw IllegalArgumentException("$errors")
    }
    return config
}


fun main(args: Array<String>) {
    val (clientType, searchConfig) = handleArguments(args)

    @Incomplete("todo setup search config updates here")
    val inputSequence = prepareUserInput()


    when (clientType) {
        is ConsoleClientType.LocalIndex -> {
            startLocalClient(clientType, searchConfig, inputSequence)
        }

        is ConsoleClientType.RemoteIndex -> {
            startRemoteClient(clientType, searchConfig, inputSequence)
        }
    }
}


/**
 * Transforms input form stdin into a sequence of strings, one for each line
 */
private fun prepareUserInput() = sequence<String> {
    println("Engine started")
    print("query>")

    var input = readLine()
    while (input != null && input != "exit") {
        yield(input)
        print("\nquery>")
        input = readLine()
    }
}

