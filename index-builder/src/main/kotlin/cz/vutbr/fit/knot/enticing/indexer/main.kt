package cz.vutbr.fit.knot.enticing.indexer

import cz.vutbr.fit.knot.enticing.api.ManagementServiceApi
import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.index.startIndexing
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.loggerFactoryFor
import cz.vutbr.fit.knot.enticing.log.measure

fun handleArguments(vararg args: String, loadConfig: (path: String) -> EnticingConfiguration = ::executeScript): Pair<EnticingConfiguration, String> {
    args.size == 2 || throw IllegalArgumentException("format: /path/to/config.kts serverAddress")
    val config = loadConfig(args[0]).validateOrFail()
    return config to args[1]
}

fun main(args: Array<String>) {
    val (enticingConfiguration, address) = handleArguments(*args)
    val config = enticingConfiguration.indexServerByAddress(address)

    val loggerFactory = enticingConfiguration.loggingConfiguration.loggerFactoryFor("$address-builder")
    val managementApi = ManagementServiceApi(enticingConfiguration.managementServiceConfiguration.fullAddress, ComponentType.INDEX_BUILDER, address, loggerFactory)
    loggerFactory.addRemoteApi(managementApi)
    val logger = loggerFactory.namedLogger("Index-Builder")


    logger.measure("indexing", config.toString()) {
        for ((collection, mg4jDir, indexDir) in config.loadCollections()) {
            try {
                logger.measure("collection ${collection.name}") {
                    val builderConf = IndexBuilderConfig(config.corpus.name, collection.name, mg4jDir, indexDir, config.metadataConfiguration
                            ?: config.corpus.metadataConfiguration)

                    startIndexing(builderConf, loggerFactory)
                }
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }
        }
    }
}

