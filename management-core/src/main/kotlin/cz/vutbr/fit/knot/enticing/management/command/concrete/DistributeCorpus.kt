package cz.vutbr.fit.knot.enticing.management.command.concrete

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.CorpusSourceConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.IndexServerConfiguration
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.management.command.ManagementCommand
import cz.vutbr.fit.knot.enticing.management.model.Mg4jFile
import cz.vutbr.fit.knot.enticing.management.shell.CopyFilesCommand
import cz.vutbr.fit.knot.enticing.management.shell.CreateRemoteDirCommand
import cz.vutbr.fit.knot.enticing.management.shell.ShellCommandExecutor
import cz.vutbr.fit.knot.enticing.management.shell.loadMg4jFiles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class DistributeCorpus(val corpusName: String) : ManagementCommand() {

    private lateinit var corpusConfiguration: CorpusConfiguration
    private lateinit var corpusSourceConfiguration: CorpusSourceConfiguration

    override fun init(configuration: EnticingConfiguration, executor: ShellCommandExecutor, logService: MeasuringLogService) {
        super.init(configuration, executor, logService)
        corpusConfiguration = configuration.corpuses.getValue(corpusName)
        corpusSourceConfiguration = corpusConfiguration.corpusSourceConfiguration
    }

    override suspend fun execute(scope: CoroutineScope) {
        val (sourceServer, directory, collectionsPerServer) = corpusSourceConfiguration
        val allFiles = shellExecutor.loadMg4jFiles(username, sourceServer, directory)

        val divided = divideFiles(corpusConfiguration.indexServers, allFiles)
        logger.info("Found ${allFiles.size} files")

        var total = 0
        for ((server, files) in divided) {
            logger.info("Server ${server.address}, ${files.size} files")
            total += files.size
        }
        check(allFiles.size == total) { "the amount of distributed files is not equal to the original amount of files" }

        divided.forEach { scope.sendToServer(it.first, collectionsPerServer, it.second) }

    }

    private fun CoroutineScope.sendToServer(server: IndexServerConfiguration, collectionCount: Int, files: List<Mg4jFile>) {
        val collections = (1..collectionCount).map { "col$it" }
        val divided = divideFiles(collections, files)
        val outputDir = server.collectionsDir ?: server.corpus.collectionsDir

        shellExecutor.execute(CreateRemoteDirCommand(username, server.address!!, outputDir))

        for ((collection, collectionFiles) in divided) {
            launch {
                val collectionDir = "$outputDir/$collection"
                shellExecutor.execute(CreateRemoteDirCommand(username, server.address!!, collectionDir))
                shellExecutor.execute(CopyFilesCommand(username, corpusSourceConfiguration.server, collectionFiles, server.address!!, collectionDir))
            }
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

