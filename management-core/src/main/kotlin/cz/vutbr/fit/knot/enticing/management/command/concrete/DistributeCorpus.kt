package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.IndexServerConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.model.Mg4jFile
import cz.vutbr.fit.knot.enticing.management.shell.LocalCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.loadMg4jFiles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

data class DistributeCorpus(val corpusName: String) : ManagementCommand() {
    override fun execute(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) {
        val logger = logService.logger { }

        val corpusConfiguration = configuration.corpuses.getValue(corpusName)
        val (sourceServer, directory, collectionsPerServer) = corpusConfiguration.corpusSourceConfiguration
        val allFiles = executor.loadMg4jFiles(configuration.authentication.username, sourceServer, directory)

        val divided = divideFiles(corpusConfiguration.indexServers, allFiles)
        logger.info("Found ${allFiles.size} files")

        var total = 0
        for ((server, files) in divided) {
            logger.info("Server ${server.address}, ${files.size} files")
            total += files.size
        }
        check(allFiles.size == total) { "the amount of distributed files is not equal to the original amount of files" }

        divided.forEach { sendToServer(configuration, it.first, collectionsPerServer, it.second) }

    }

    private fun sendToServer(configuration: EnticingConfiguration, server: IndexServerConfiguration, collectionCount: Int, files: List<Mg4jFile>) = runBlocking {
        val collections = (1..collectionCount).map { "col$it" }
        val divided = divideFiles(collections, files)
        val username = configuration.authentication.username
        withContext(Dispatchers.IO) {
            val outputDir = server.collectionsDir ?: server.corpus.collectionsDir
            val command = LocalCommand("scp ")
        }
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

