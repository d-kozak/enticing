package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.command.NewManagementCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.loadFiles
import cz.vutbr.fit.knot.enticing.management.shell.loadMg4jFiles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

open class ShowDistributedFilesCommand(
        val corpus: CorpusConfiguration,
        loggerFactory: LoggerFactory
) : NewManagementCommand() {

    private val logger = loggerFactory.logger { }

    override suspend fun execute(scope: CoroutineScope, executor: ShellCommandExecutor) = coroutineScope {
        val totalStats = corpus.indexServers.map { server ->
            async {
                val collectionDir = server.collectionsDir ?: server.corpus.collectionsDir
                val collections = executor.loadFiles(server.address, collectionDir)

                logger.info("server ${server.address}, collections $collections")

                val collectionsContent = collections.map { collection ->
                    async {
                        val files = executor.loadMg4jFiles(server.address, "$collectionDir/$collection/mg4j")
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