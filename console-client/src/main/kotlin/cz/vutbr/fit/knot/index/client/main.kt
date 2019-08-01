package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientType
import cz.vutbr.fit.knot.enticing.dto.config.executeScript

fun handleArguments(args: Array<String>): ConsoleClientConfig {
    args.size == 1 || throw IllegalArgumentException("Exactly one argument expected, the config file")
    val config = executeScript<ConsoleClientConfig>(args[0])
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

