package cz.vutbr.fit.knot.enticing.index

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.mg4jFiles
import cz.vutbr.fit.knot.enticing.index.mg4j.DirectoryIOFactory
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jCompositeDocumentCollection
import it.unimi.di.big.mg4j.tool.IndexBuilder

/**
 * Executes indexing based on the configuration passed in
 */
fun startIndexing(config: IndexBuilderConfig) {
    val documentCollection = Mg4jCompositeDocumentCollection(config.metadataConfiguration, config.inputDir.mg4jFiles)
    IndexBuilder(config.corpusName, documentCollection)
            .ioFactory(DirectoryIOFactory(config.outputDir.toPath()))
            .run()
}