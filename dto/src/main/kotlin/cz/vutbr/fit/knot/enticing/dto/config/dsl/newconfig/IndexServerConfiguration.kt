package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor.EnticingConfigurationVisitor
import java.io.File

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
         * Directory with collections that should be maintained by this server
         */
        var collectionsDir: String? = null,

        override var address: String? = null
) : ComponentConfiguration {

    /**
     * corpus this index server belongs to
     */
    lateinit var corpus: CorpusConfiguration

    fun loadCollections(): Sequence<Triple<File, File, File>> = sequence {
        val inputDir = File(collectionsDir ?: corpus.collectionsDir)
        for (dir in inputDir.listFiles() ?: emptyArray()) {
            if (!dir.isDirectory) continue
            val files = dir.listFiles() ?: emptyArray()
            val mg4jDir = files.find { it.name == "mg4j" } ?: throw IllegalArgumentException("mg4j dir not found")
            val indexDir = files.find { it.name == "indexed" } ?: throw IllegalStateException("indexed dir not found")
            yield(Triple(inputDir, mg4jDir, indexDir))
        }
    }

    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitIndexServerConfiguration(this)
    }

    fun metadata(block: MetadataConfiguration.() -> Unit) {
        metadataConfiguration = MetadataConfiguration().apply(block)
    }
}

class IndexServerList(val corpus: CorpusConfiguration, val errorCatcher: (() -> Unit) -> Unit) {

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