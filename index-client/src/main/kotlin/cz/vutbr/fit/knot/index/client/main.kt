package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.index.config.dsl.IndexClientConfig
import cz.vutbr.fit.knot.enticing.index.config.executeScript
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jCompositeDocumentCollection
import it.unimi.di.big.mg4j.index.Index
import it.unimi.di.big.mg4j.index.TermProcessor
import it.unimi.di.big.mg4j.query.IntervalSelector
import it.unimi.di.big.mg4j.query.Query
import it.unimi.di.big.mg4j.query.QueryEngine
import it.unimi.di.big.mg4j.query.SelectedInterval
import it.unimi.di.big.mg4j.query.parser.SimpleParser
import it.unimi.di.big.mg4j.search.DocumentIteratorBuilderVisitor
import it.unimi.di.big.mg4j.search.score.DocumentScoreInfo
import it.unimi.dsi.fastutil.objects.*
import java.io.InputStreamReader

fun handleArguments(args: Array<String>): IndexClientConfig {
    args.size != 1 && throw IllegalArgumentException("Expecting exactly one argument, config file")
    return executeScript(args[0])
}

fun main(args: Array<String>) {
    val config = handleArguments(args)

    val (collection, engine) = initQueryEngine(config)
    responseLoop(collection, config, engine)
}

fun responseLoop(collection: Mg4jCompositeDocumentCollection, config: IndexClientConfig, engine: QueryEngine, readQuery: () -> String? = ::readLine) {
    val resultList = ObjectArrayList<DocumentScoreInfo<Reference2ObjectMap<Index, Array<SelectedInterval>>>>()

    val tokenIndex = config.corpusConfiguration.indexes["token"]!!.columnIndex

    println("Engine started")
    print("query>")
    var input = readQuery()
    while (input != null && input != "quit") {
        resultList.clear()
        val processed = engine.process(input, 0, 100, resultList)
        println("Processed $processed documents")

        for (result in resultList) {
            val document = collection.document(result.document)
            val scores = result.info[engine.indexMap["token"]]!!

            println("===========================")
            println(document.title())

            for (score in scores) {
                val reader = document.content(tokenIndex) as InputStreamReader
                val regex = """\s+"""
                val words = reader.readText().split(regex.toRegex())


                val left = score.interval.left
                val prefix = Math.max(left - 5, 0)
                val right = score.interval.right

                val suffix = Math.min(right + 5, words.size)

                val wordList = mutableListOf<String>()
                        .also {
                            it.addAll(words.subList(prefix, left))
                            it.add("<b>")
                            it.addAll(words.subList(left, right + 1))
                            it.add("</b>")
                            it.addAll(words.subList(right + 1, suffix))
                        }

                val snippet = wordList.joinToString(separator = " ")
                println("=> $snippet")
            }
            println("===========================")
        }
        print("\nquery>")
        input = readQuery()
    }
}

internal fun initQueryEngine(config: IndexClientConfig): Pair<Mg4jCompositeDocumentCollection, QueryEngine> {
    val collection = Mg4jCompositeDocumentCollection(config.indexes, config.mg4jFiles)

    val indexDir = config.indexDirectory

    val indexMap = Object2ReferenceLinkedOpenHashMap<String, Index>()
    val termProcessors = Object2ObjectOpenHashMap<String, TermProcessor>()
    val index2weight = Reference2DoubleOpenHashMap<Index>()
    for (index in config.indexes) {
        val mg4jIndex = Index.getInstance(indexDir.resolve("${config.corpusConfiguration.corpusName}-${index.name}").path)
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
    return collection to engine
}