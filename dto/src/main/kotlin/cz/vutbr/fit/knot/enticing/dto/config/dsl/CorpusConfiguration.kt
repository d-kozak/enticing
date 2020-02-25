package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationVisitor
import java.io.File

/**
 * Configuration of one corpus
 */
data class CorpusConfiguration(
        /**
         * name of the corpus
         */
        var name: String,
        /**
         * metadata configuration for this corpus
         */
        var metadataConfiguration: MetadataConfiguration = MetadataConfiguration(),
        /**
         * list of index servers that belong to this corpus
         */
        var indexServers: MutableList<IndexServerConfiguration> = mutableListOf(),

        /**
         * Directory with collections that should be maintained by this server
         */
        var collectionsDir: String = "",
        /**
         * Information about the location of the original files of that corpus before distribution
         */
        var corpusSourceConfiguration: CorpusSourceConfiguration = CorpusSourceConfiguration()
) : EnticingConfigurationUnit {


    lateinit var errorCatcher: (() -> Unit) -> Unit

    fun corpusSource(block: CorpusSourceConfiguration.() -> Unit) {
        corpusSourceConfiguration = CorpusSourceConfiguration().apply(block)
    }

    fun indexServers(block: IndexServerList.() -> Unit) {
        indexServers = IndexServerList(this, errorCatcher).apply(block).indexList
    }

    fun serverFile(path: String) {
        val servers = File(path).readLines()
        indexServers = servers.map { line ->
            val separator = line.indexOf(":")
            val (address, port) = if (separator >= 0) {
                line.substring(0, separator) to line.substring(separator + 1).toInt()
            } else line to IndexServerConfiguration.DEFAULT_PORT
            IndexServerConfiguration().also {
                it.address = address
                it.port = port
                it.corpus = this
            }
        }.toMutableList()
    }

    fun metadata(block: MetadataConfiguration.() -> Unit) {
        metadataConfiguration = MetadataConfiguration().apply(block)
    }

    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitCorpusConfiguration(this)
    }
}


data class CorpusMap(val errorCatcher: (() -> Unit) -> Unit) {

    val corpusMap: MutableMap<String, CorpusConfiguration> = mutableMapOf()

    fun corpus(name: String, block: CorpusConfiguration.() -> Unit) {
        errorCatcher {
            corpusMap[name] = CorpusConfiguration(name)
                    .also { it.errorCatcher = errorCatcher }
                    .apply(block)
        }
    }
}