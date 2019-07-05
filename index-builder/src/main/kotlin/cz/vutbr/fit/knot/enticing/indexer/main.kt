package cz.vutbr.fit.knot.enticing.indexer

import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.index.startIndexing
import java.io.File

fun handleArguments(vararg args: String, loadConfig: (path: String) -> IndexBuilderConfig = ::executeScript): IndexBuilderConfig {
    args.isEmpty() && throw IllegalArgumentException("At least one argument necessary, the config file path")
    args.size == 2 && throw IllegalArgumentException("Two arguments are ambiguous, provide just the config file or at least three")
    val config = loadConfig(args[0])
    if (args.size > 1) {
        config.input = (1..args.size - 2).map { File(args[it]) }
        config.output = File(args[args.size - 1])
    }

    if (!config.output.exists()) {
        config.output.mkdir() || throw IllegalArgumentException("Could not create output directory ${config.output}")
    }
    config.output.isDirectory || throw IllegalArgumentException("${config.output} is not a directory")
    return config
}

fun main(args: Array<String>) {
    val config = handleArguments(*args)
    println(config)

    startIndexing(config)
}
