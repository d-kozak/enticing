package cz.vutbr.fit.knot.enticing.indexer

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.index.startIndexing
import cz.vutbr.fit.knot.enticing.log.configureFor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun handleArguments(vararg args: String, loadConfig: (path: String) -> EnticingConfiguration = ::executeScript): Pair<EnticingConfiguration, String> {
    args.size == 2 || throw IllegalArgumentException("format: /path/to/config.kts serverAddress")
    val config = loadConfig(args[0]).validateOrFail()
    return config to args[1]
}

suspend fun main(args: Array<String>) {
    val (enticingConfiguration, address) = handleArguments(*args)
    val config = enticingConfiguration.indexServerByAddress(address)

    val logger = enticingConfiguration.loggingConfiguration.configureFor("$address-builder")

    logger.measure("indexing") {
        withContext(Dispatchers.Default) {
            for ((collection, mg4jDir, indexDir) in config.loadCollections()) {
                launch {
                    val builderConf = IndexBuilderConfig(config.corpus.name, collection.name, mg4jDir, indexDir, config.metadataConfiguration
                            ?: config.corpus.metadataConfiguration)
                    try {
                        startIndexing(builderConf)
                    } catch (ex: java.lang.Exception) {
                        ex.printStackTrace()
                    }
                }
            }
        }
    }
}
