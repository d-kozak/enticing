package cz.vutbr.fit.knot.enticing.indexer

import cz.vutbr.fit.knot.enticing.index.config.dsl.IndexerConfig
import cz.vutbr.fit.knot.enticing.index.mg4j.DirectoryIOFactory
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jCompositeDocumentCollection
import cz.vutbr.fit.knot.enticing.indexer.configuration.loadConfiguration
import it.unimi.di.big.mg4j.tool.IndexBuilder
import java.io.File

fun handleArguments(vararg args: String, loadConfig: (path: String) -> IndexerConfig = ::loadConfiguration): IndexerConfig {
    args.isEmpty() && throw IllegalArgumentException("At least one argument necessary, the config file path")
    args.size == 2 && throw IllegalArgumentException("Two arguments are ambiguous, provide just the config file or at least three")
    val config = loadConfig(args[0])
    if (args.size > 1) {
        config.input = (1..args.size - 2).map { File(args[it]) }
        config.output = File(args[args.size - 1])
    }
    return config
}

fun startIndexing(config: IndexerConfig) {
    val documentCollection = Mg4jCompositeDocumentCollection(config.indexes, config.input)
    IndexBuilder(config.corpusConfiguration.corpusName, documentCollection)
            .ioFactory(DirectoryIOFactory(config.output.toPath()))
            .run()
}

fun main(args: Array<String>) {
    val config = handleArguments(*args)
    println(config)

    startIndexing(config)
}
