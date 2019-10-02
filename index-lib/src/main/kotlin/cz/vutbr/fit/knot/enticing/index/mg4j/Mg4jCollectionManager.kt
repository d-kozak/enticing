package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.dto.annotation.WhatIf
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CollectionConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
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

fun initMg4jCollectionManager(corpusConfiguration: CorpusConfiguration, collectionConfig: CollectionConfiguration): CollectionManager {
    val collection = Mg4jCompositeDocumentCollection(corpusConfiguration, collectionConfig.mg4jFiles)
    val engine = initMg4jQueryEngine(collectionConfig, corpusConfiguration)

    val mg4jSearchEngine = Mg4jSearchEngine(collection, engine)
    return CollectionManager(collectionConfig.name, mg4jSearchEngine, EqlPostProcessor(), EqlResultCreator(corpusConfiguration), EqlCompiler(corpusConfiguration))
}

@WhatIf("default index is hardwired to token internally, is it enough or should we provide a way to tweak this? maybe as an AST operation in EQL-compiler (to avoid expensive reinitialization of mg4j internals)?")
private fun initMg4jQueryEngine(collectionConfig: CollectionConfiguration, corpusConfiguration: CorpusConfiguration): QueryEngine {
    val indexDir = collectionConfig.indexDirectory

    val indexMap = Object2ReferenceLinkedOpenHashMap<String, Index>()
    val termProcessors = Object2ObjectOpenHashMap<String, TermProcessor>()
    val index2weight = Reference2DoubleOpenHashMap<Index>()
    for (index in corpusConfiguration.indexes.values.toList()) {
        val mg4jIndex = Index.getInstance(indexDir.resolve("${corpusConfiguration.corpusName}-${index.name}").path)
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