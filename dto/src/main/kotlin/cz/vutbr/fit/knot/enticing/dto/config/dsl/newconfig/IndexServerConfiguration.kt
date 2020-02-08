package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration

/**
 * Configuration of the index server
 * of it's options are null, then they are supposed to be taken from the corpus configuration
 */
data class IndexServerConfiguration(
        /**
         * metadata configuration for this server
         */
        var metadata: MetadataConfiguration? = null,

        /**
         * Directory with mg4j files that should be maintained by this server
         */
        var mg4jDir: String? = null,

        /**
         * Directory containing metadata for files in mg4jDir
         */
        var indexedDir: String? = null,
        override var address: String? = null
) : ComponentConfiguration {

    /**
     * corpus this index server belongs to, null means it does not belong to any
     */
    var corpus: CorpusConfiguration? = null
}

class IndexServerList(val corpus: CorpusConfiguration? = null, val indexList: MutableList<IndexServerConfiguration> = mutableListOf()) {

    fun indexServer(block: IndexServerConfiguration.() -> Unit) = IndexServerConfiguration()
            .apply(block)
            .also {
                indexList.add(it)
                it.corpus = corpus
            }
}