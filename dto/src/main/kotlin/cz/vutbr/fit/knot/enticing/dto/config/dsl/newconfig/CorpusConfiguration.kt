package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

import java.io.File

/**
 * Configuration of one corpus
 */
class CorpusConfiguration {
    /**
     * metadata configuration for this corpus
     */
    lateinit var metadata: MetadataConfiguration

    /**
     * list of index servers that belong to this corpus
     */
    var indexServers = mutableListOf<IndexServerConfiguration>()

    /**
     * Directory with mg4j files that should be maintained by this server
     */
    var mg4jDir: String? = null

    /**
     * Directory containing metadata for files in mg4jDir
     */
    var indexedDir: String? = null

    fun indexServers(block: IndexServerList.() -> Unit) {
        indexServers = IndexServerList().apply(block).indexList
    }

    fun serverFile(path: String) {
        val servers = File(path).readLines()
        indexServers = servers.map { address ->
            IndexServerConfiguration().also { it.address = address }
        }.toMutableList()
    }

}


data class CorpusListDsl(val corpusList: MutableList<CorpusConfiguration> = mutableListOf()) {

    fun corpus(block: CorpusConfiguration.() -> Unit) = CorpusConfiguration()
            .apply(block)
            .also {
                corpusList.add(it)
            }
}