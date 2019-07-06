package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientType
import cz.vutbr.fit.knot.enticing.dto.config.executeScript

fun handleArguments(args: Array<String>): ConsoleClientConfig {
    args.size == 1 || throw IllegalArgumentException("Exactly one argument expected, the config file")
    return executeScript(args[0])
}


fun main(args: Array<String>) {
    val (clientType, searchConfig) = handleArguments(args)

    // todo setup search config updates here

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

