package cz.vutbr.fit.knot.enticing.indexer

import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.index.startIndexing
import cz.vutbr.fit.knot.enticing.log.SimpleDirectoryBasedLogService
import java.io.File

fun handleArguments(vararg args: String, loadConfig: (path: String) -> IndexBuilderConfig = ::executeScript): IndexBuilderConfig {
    args.size < 4 && throw IllegalArgumentException("format: /path/to/config.kts [collectionId] [input1 ... inputN] [output]")
    val config = loadConfig(args[0])
    config.collection(args[1])
    if (args.size == 4) {
        val input = File(args[2])
        if (input.isDirectory) {
            config.inputDirectory(args[2])
        } else {
            config.inputFiles(args[2])
        }
        config.outputDirectory(args[3])
    } else {
        config.inputFiles(args.toList().subList(2, args.size - 1))
        config.outputDirectory(args.last())
    }

    val errors = config.validate()
    if (errors.isNotEmpty()) {
        throw IllegalArgumentException("$errors")
    }

    return config
}

fun main(args: Array<String>) {
    val config = handleArguments(*args)
    println(config)

    val logger = SimpleDirectoryBasedLogService(config.collectionName, config.logDirectory)
    logger.measure("indexing")

    try {
        startIndexing(config)
        logger.reportSuccess("finished", "indexing")
    } catch (ex: Exception) {
        ex.printStackTrace()
        logger.reportCrash(ex, "indexing")
    }
}
