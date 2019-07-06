package cz.vutbr.fit.knot.enticing.indexer

import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.index.startIndexing

fun handleArguments(vararg args: String, loadConfig: (path: String) -> IndexBuilderConfig = ::executeScript): IndexBuilderConfig {
    args.size < 3 && throw IllegalArgumentException("At least three arguments necessary: config_file input_dir output_dir")
    val config = loadConfig(args[0])
    if (args.size == 3) {
        config.inputDirectory(args[1])
        config.outputDirectory(args[2])
    } else {
        config.inputFiles(args.toList().subList(1, args.size - 1))
        config.outputDirectory(args.last())
    }

    return config
}

fun main(args: Array<String>) {
    val config = handleArguments(*args)
    println(config)

    startIndexing(config)
}
