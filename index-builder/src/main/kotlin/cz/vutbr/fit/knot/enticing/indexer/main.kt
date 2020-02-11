package cz.vutbr.fit.knot.enticing.indexer

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.IndexServerConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.index.startIndexing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun handleArguments(vararg args: String, loadConfig: (path: String) -> EnticingConfiguration = ::executeScript): IndexServerConfiguration {
    args.size == 2 || throw IllegalArgumentException("format: /path/to/config.kts serverAddress")
    val config = loadConfig(args[0]).validateOrFail()
    return config.indexServerByAddress(args[1])
}

suspend fun main(args: Array<String>) {
    val config = handleArguments(*args)
    println(config)

    withContext(Dispatchers.Default) {
        for ((collection, mg4jDir, indexDir) in config.loadCollections()) {
            launch {
                val builderConf = IndexBuilderConfig(config.corpus.name, collection.name, config.metadataConfiguration
                        ?: config.corpus.metadataConfiguration, mg4jDir, indexDir)
                try {
                    startIndexing(builderConf)
                } catch (ex: java.lang.Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }


//    val logger = SimpleDirectoryBasedLogService(config.collectionName, config.logDirectory) todo logging
//    logger.use {
//        logger.measure("indexing")
//    try {
//        startIndexing(config)
////            logger.reportSuccess("finished", "indexing")
//    } catch (ex: Exception) {
//        ex.printStackTrace()
////            logger.reportCrash(ex, "indexing")
//    }
//}
}
