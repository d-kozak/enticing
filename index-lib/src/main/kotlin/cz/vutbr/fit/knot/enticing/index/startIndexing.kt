package cz.vutbr.fit.knot.enticing.index

import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.mg4jFiles
import cz.vutbr.fit.knot.enticing.index.mg4j.DirectoryIOFactory
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jCompositeDocumentCollection
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import it.unimi.di.big.mg4j.tool.IndexBuilder

/**
 * Executes indexing based on the configuration passed in
 */
fun startIndexing(config: IndexBuilderConfig, logService: MeasuringLogService) {
    val documentCollection = Mg4jCompositeDocumentCollection(config.metadataConfiguration, config.mg4jDir.mg4jFiles, logService)
    IndexBuilder(config.corpusName, documentCollection)
            .ioFactory(DirectoryIOFactory(config.indexDir.toPath()))
            .run()
}