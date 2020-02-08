package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor.EnticingConfigurationVisitor

/**
 * Configuration of the index server
 * of it's options are null, then they are supposed to be taken from the corpus configuration
 */
data class IndexServerConfiguration(
        /**
         * metadata configuration for this server
         */
        var metadataConfiguration: MetadataConfiguration? = null,

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

    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitIndexServerConfiguration(this)
    }

    fun metadata(block: MetadataConfiguration.() -> Unit) {
        metadataConfiguration = MetadataConfiguration().apply(block)
    }
}

class IndexServerList(val corpus: CorpusConfiguration? = null, val errorCatcher: (() -> Unit) -> Unit) {

    val indexList: MutableList<IndexServerConfiguration> = mutableListOf()

    fun indexServer(block: IndexServerConfiguration.() -> Unit) {
        errorCatcher {
            IndexServerConfiguration()
                    .apply(block)
                    .also {
                        indexList.add(it)
                        it.corpus = corpus
                    }
        }
    }
}