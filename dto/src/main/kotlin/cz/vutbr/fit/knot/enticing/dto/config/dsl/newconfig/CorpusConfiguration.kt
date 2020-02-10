package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor.EnticingConfigurationVisitor
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
        var collectionsDir: String = "") : EnticingConfigurationUnit {


    lateinit var errorCatcher: (() -> Unit) -> Unit

    fun indexServers(block: IndexServerList.() -> Unit) {
        indexServers = IndexServerList(this, errorCatcher).apply(block).indexList
    }

    fun serverFile(path: String) {
        val servers = File(path).readLines()
        indexServers = servers.map { address ->
            IndexServerConfiguration().also {
                it.address = address
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


data class CorpusList(val errorCatcher: (() -> Unit) -> Unit) {

    val corpusList: MutableList<CorpusConfiguration> = mutableListOf()

    fun corpus(name: String, block: CorpusConfiguration.() -> Unit) {
        errorCatcher {
            corpusList.add(CorpusConfiguration(name)
                    .also {
                        it.errorCatcher = errorCatcher
                    }
                    .apply(block))
        }
    }
}