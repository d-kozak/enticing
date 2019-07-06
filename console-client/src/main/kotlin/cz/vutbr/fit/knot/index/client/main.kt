package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientType
import cz.vutbr.fit.knot.enticing.dto.config.executeScript

fun handleArguments(args: Array<String>): ConsoleClientConfig = executeScript(args[0])


fun main(args: Array<String>) {
    val config = handleArguments(args)
    val inputSequence = prepareUserInput()

    when (val clientType = config.clientType) {
        is ConsoleClientType.LocalIndex -> {
            startLocalClient(config, inputSequence)
        }

        is ConsoleClientType.RemoteIndex -> {
            TODO("finish")
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

