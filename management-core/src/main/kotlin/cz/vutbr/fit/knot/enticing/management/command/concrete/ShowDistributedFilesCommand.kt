package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.command.CorpusSpecificCommandContext
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.loadFiles
import cz.vutbr.fit.knot.enticing.management.shell.loadMg4jFiles
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

data class ShowDistributedFiles(val corpusName: String) : ManagementCommand<ShowDistributedFilesContext>() {
    override fun buildContext(configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory): ShowDistributedFilesContext = ShowDistributedFilesContext(corpusName, configuration, executor, loggerFactory)
}

class ShowDistributedFilesContext(corpusName: String, configuration: EnticingConfiguration, executor: ShellCommandExecutor, loggerFactory: LoggerFactory) : CorpusSpecificCommandContext(corpusName, configuration, executor, loggerFactory) {

    private val logger = loggerFactory.logger { }

    override suspend fun execute() = coroutineScope {
        val totalStats = corpusConfiguration.indexServers.map { server ->
            async {
                val collectionDir = server.collectionsDir ?: server.corpus.collectionsDir
                val collections = shellExecutor.loadFiles(username, server.address, collectionDir)

                logger.info("server ${server.address}, collections $collections")

                val collectionsContent = collections.map { collection ->
                    async {
                        val files = shellExecutor.loadMg4jFiles(username, server.address, "$collectionDir/$collection/mg4j")
                        for (file in files) {
                            logger.info("server ${server.address}, collection $collection, files: $files")
                        }
                        collection to files
                    }
                }.awaitAll()

                server to collectionsContent
            }
        }.awaitAll()

        var fileSize = 0L
        var fileCount = 0

        for ((server, collections) in totalStats) {
            logger.info("server ${server.address}")
            for ((collection, files) in collections) {
                logger.info("\t $collection")
                fileCount += files.size
                for (file in files) {
                    logger.info("\t\t $file")
                    fileSize += file.size
                }
            }
        }
        logger.info("Total file count $fileCount")
        logger.info("Total file size ${fileSize}B")
    }
}