package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.dto.annotation.WhatIf
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.CollectionManagerConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.mg4jFiles
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.index.collection.manager.CollectionManager
import cz.vutbr.fit.knot.enticing.index.collection.manager.format.result.EqlResultCreator
import cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.EqlPostProcessor
import it.unimi.di.big.mg4j.index.Index
import it.unimi.di.big.mg4j.index.TermProcessor
import it.unimi.di.big.mg4j.query.IntervalSelector
import it.unimi.di.big.mg4j.query.Query
import it.unimi.di.big.mg4j.query.QueryEngine
import it.unimi.di.big.mg4j.query.parser.SimpleParser
import it.unimi.di.big.mg4j.search.DocumentIteratorBuilderVisitor
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import it.unimi.dsi.fastutil.objects.Object2ReferenceLinkedOpenHashMap
import it.unimi.dsi.fastutil.objects.Reference2DoubleOpenHashMap

fun initMg4jCollectionManager(configuration: CollectionManagerConfiguration): CollectionManager {
    val collection = Mg4jCompositeDocumentCollection(configuration.metadataConfiguration, configuration.mg4jDir.mg4jFiles)
    val engine = initMg4jQueryEngine(configuration)

    val mg4jSearchEngine = Mg4jSearchEngine(collection, engine)
    return CollectionManager("${configuration.corpusName}-${configuration.collectionName}", mg4jSearchEngine, EqlPostProcessor(), EqlResultCreator(configuration.metadataConfiguration), EqlCompiler(), configuration.metadataConfiguration)
}

@WhatIf("default index is hardwired to token internally, is it enough or should we provide a way to tweak this? maybe as an AST operation in EQL-compiler (to avoid expensive reinitialization of mg4j internals)?")
internal fun initMg4jQueryEngine(configuration: CollectionManagerConfiguration): QueryEngine {
    val indexDir = configuration.indexDir

    val indexMap = Object2ReferenceLinkedOpenHashMap<String, Index>()
    val termProcessors = Object2ObjectOpenHashMap<String, TermProcessor>()
    val index2weight = Reference2DoubleOpenHashMap<Index>()
    for (index in configuration.metadataConfiguration.indexes.values.toList()) {
//        if (index.isSynthetic) continue todo glue index
        val mg4jIndex = Index.getInstance(indexDir.resolve("${configuration.corpusName}-${index.name}").path)
        requireNotNull(mg4jIndex.field)
        indexMap[mg4jIndex.field] = mg4jIndex
        termProcessors[mg4jIndex.field] = mg4jIndex.termProcessor
        index2weight[mg4jIndex] = 1.0
    }

    val defaultIndex = "token"
    val engine = QueryEngine(
            SimpleParser(indexMap.keys, defaultIndex, termProcessors),
            DocumentIteratorBuilderVisitor(indexMap, indexMap[defaultIndex], Query.MAX_STEMMING),
            indexMap
    )
    engine.setWeights(index2weight)
    engine.intervalSelector = IntervalSelector(Integer.MAX_VALUE, Integer.MAX_VALUE)
    engine.multiplex = false
    return engine
}