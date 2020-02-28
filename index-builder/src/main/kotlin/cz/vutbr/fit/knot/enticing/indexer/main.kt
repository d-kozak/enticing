package cz.vutbr.fit.knot.enticing.indexer

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.index.startIndexing
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.RemoteLoggingConfigurationOld
import cz.vutbr.fit.knot.enticing.log.configureFor

fun handleArguments(vararg args: String, loadConfig: (path: String) -> EnticingConfiguration = ::executeScript): Pair<EnticingConfiguration, String> {
    args.size == 2 || throw IllegalArgumentException("format: /path/to/config.kts serverAddress")
    val config = loadConfig(args[0]).validateOrFail()
    return config to args[1]
}

fun main(args: Array<String>) {
    val (enticingConfiguration, address) = handleArguments(*args)
    val config = enticingConfiguration.indexServerByAddress(address)

    val logger = enticingConfiguration.loggingConfiguration.configureFor("$address-builder",
            RemoteLoggingConfigurationOld("$address-builder", enticingConfiguration.managementServiceConfiguration.fullAddress, ComponentType.INDEX_BUILDER))

    logger.measure("indexing") {
        for ((collection, mg4jDir, indexDir) in config.loadCollections()) {
            try {
                logger.measure("collection ${collection.name}") {
                    val builderConf = IndexBuilderConfig(config.corpus.name, collection.name, mg4jDir, indexDir, config.metadataConfiguration
                            ?: config.corpus.metadataConfiguration)

                    startIndexing(builderConf, logger)
                }
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }
        }
    }
}

