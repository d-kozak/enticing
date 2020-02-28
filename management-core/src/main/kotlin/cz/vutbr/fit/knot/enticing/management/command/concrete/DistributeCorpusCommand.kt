package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexServerConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.command.CorpusSpecificCommandContext
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.model.Mg4jFile
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.copyFiles
import cz.vutbr.fit.knot.enticing.management.shell.createRemoteDirectory
import cz.vutbr.fit.knot.enticing.management.shell.loadMg4jFiles
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

data class DistributeCorpusCommand(val corpusName: String) : ManagementCommand<DistributeCorpusContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory): DistributeCorpusContext = DistributeCorpusContext(corpusName, configuration, executor, loggerFactory)
}

class DistributeCorpusContext(corpusName: String, configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory) : CorpusSpecificCommandContext(corpusName, configuration, executor, loggerFactory) {

    private val logger = loggerFactory.logger { }

    private val finishedCollection = AtomicInteger(0)
    @Volatile
    private var totalCollections = 0

    override suspend fun execute() = coroutineScope {
        val (sourceServer, directory, collectionsPerServer, fileLimit) = corpusSourceConfiguration
        val allFiles = shellExecutor.loadMg4jFiles(username, sourceServer, directory, fileLimit)
        logger.info("Found ${allFiles.size} files")

        val dividedPerServer = divideFiles(corpusConfiguration.indexServers, allFiles)

        val dividedPerCollection = dividedPerServer.map { (server, files) ->
            val collections = (1..collectionsPerServer).map { "col$it" }
            val divided = divideFiles(collections, files)
            server to divided
        }

        var fileCount = 0
        for ((server, collections) in dividedPerCollection) {
            logger.info("Server ${server.address}:")
            var filesOnServer = 0
            for ((collection, files) in collections) {
                filesOnServer += files.size
                logger.info("\t collection $collection:")
                for (file in files) {
                    logger.info("\t\t$file")
                }
            }

            logger.info("\t Server ${server.address} will have $filesOnServer files")
            totalCollections += collections.size
            fileCount += filesOnServer
        }
        logger.info("Total distributed files $fileCount")
        check(allFiles.size == fileCount) { "the amount of distributed files is not equal to the original amount of files" }

        for (i in 0 until collectionsPerServer) {
            dividedPerCollection.map { (server, collections) ->
                val (collection, files) = collections[i]
                launch {
                    createCollection(server, collection, files)
                }
            }.joinAll()
        }
    }


    private suspend fun createCollection(server: IndexServerConfiguration, collection: String, files: List<Mg4jFile>) {
        val outputDir = server.collectionsDir ?: server.corpus.collectionsDir
        val collectionDir = "$outputDir/$collection/mg4j"
        shellExecutor.createRemoteDirectory(username, server.address, collectionDir)
        shellExecutor.copyFiles(username, corpusSourceConfiguration.server, files, server.address, collectionDir)
        logger.info("[${finishedCollection.incrementAndGet()}/$totalCollections]  Created collection $collection at server ${server.address} with files $files")
    }

    private fun <T> divideFiles(components: List<T>, files: List<Mg4jFile>): MutableList<Pair<T, List<Mg4jFile>>> {
        val res = mutableListOf<Pair<T, List<Mg4jFile>>>()
        val serverCount = components.size
        val div = files.size / serverCount
        val rem = files.size % serverCount

        var start = 0
        for ((i, server) in components.withIndex()) {
            val fileCount = if (i < rem) div + 1 else div
            val part = files.subList(start, start + fileCount)
            start += fileCount
            res.add(server to part)
        }
        return res
    }
}
