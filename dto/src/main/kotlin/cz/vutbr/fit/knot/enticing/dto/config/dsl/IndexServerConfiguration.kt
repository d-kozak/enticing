package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationVisitor
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

        override var address: String = ""
) : ComponentConfiguration {

    /**
     * corpus this index server belongs to
     */
    lateinit var corpus: CorpusConfiguration

    fun loadCollections(): Sequence<Triple<File, File, File>> = sequence {
        val inputDir = File(collectionsDir ?: corpus.collectionsDir)
        for (dir in inputDir.listFiles() ?: emptyArray()) {
            if (!dir.isDirectory || !dir.name.startsWith("col")) continue
            val mg4jDir = File(dir.path + File.separatorChar + "mg4j")
            check(mg4jDir.exists()) { "mg4j ${mg4jDir.path} directory does not exist" }
            check(mg4jDir.isDirectory()) { "${mg4jDir.path} is not a directory" }
            val indexDir = File(dir.path + File.separatorChar + "indexed")
            if (!indexDir.exists()) {
                check(indexDir.mkdirs()) { "failed to create index dir for indexing" }
            } else {
                check(indexDir.isDirectory()) { "${indexDir.path} is not a directory" }
            }
            yield(Triple(dir, mg4jDir, indexDir))
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