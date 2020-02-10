package cz.vutbr.fit.knot.enticing.index

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.index.mg4j.DirectoryIOFactory
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jCompositeDocumentCollection
import it.unimi.di.big.mg4j.tool.IndexBuilder
import java.io.File

// todo refactor
val File.mg4jFiles: List<File>
    get() = this.listFiles { file -> file.name.endsWith(".mg4j") }?.toList() ?: emptyList()

data class IndexBuilderConfig(
        val corpusName: String,
        val collectionName: String,
        val metadataConfiguration: MetadataConfiguration,
        val inputDir: File,
        val outputDir: File
)

/**
 * Executes indexing based on the configuration passed in
 */
fun startIndexing(config: IndexBuilderConfig) {
    val documentCollection = Mg4jCompositeDocumentCollection(config.metadataConfiguration, config.inputDir.mg4jFiles)
    IndexBuilder(config.corpusName, documentCollection)
            .ioFactory(DirectoryIOFactory(config.outputDir.toPath()))
            .run()
}