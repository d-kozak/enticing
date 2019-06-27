package cz.vutbr.fit.knot.enticing.index

import cz.vutbr.fit.knot.enticing.index.config.dsl.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.index.mg4j.DirectoryIOFactory
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jCompositeDocumentCollection
import it.unimi.di.big.mg4j.tool.IndexBuilder

fun startIndexing(config: IndexBuilderConfig) {
    val documentCollection = Mg4jCompositeDocumentCollection(config.indexes, config.input)
    IndexBuilder(config.corpusConfiguration.corpusName, documentCollection)
            .ioFactory(DirectoryIOFactory(config.output.toPath()))
            .run()
}