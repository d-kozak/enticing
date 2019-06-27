package cz.vutbr.fit.knot.enticing.index

import cz.vutbr.fit.knot.enticing.dto.query.Offset
import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.response.*
import cz.vutbr.fit.knot.enticing.index.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.index.config.dsl.IndexClientConfig
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jCompositeDocumentCollection
import cz.vutbr.fit.knot.enticing.index.mg4j.Mg4jDocumentFactory
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
import it.unimi.dsi.util.Interval
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.InputStreamReader

fun initQueryEngine(config: IndexClientConfig): QueryExecutor {
    val collection = Mg4jCompositeDocumentCollection(config.indexes, config.mg4jFiles)
    val factory = Mg4jDocumentFactory(config.indexes)

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

    return QueryExecutor("name", collection, factory, engine, config.corpusConfiguration)
}

class QueryExecutor(
        private val collectionName: String,
        private val collection: Mg4jCompositeDocumentCollection,
        private val factory: Mg4jDocumentFactory,
        private val engine: QueryEngine,
        private val corpusConfiguration: CorpusConfiguration
) {

    private val log: Logger = LoggerFactory.getLogger(QueryExecutor::class.java)

    fun query(query: SearchQuery): SearchResult {
        log.info("Executing query $query")
        val resultList = ObjectArrayList<DocumentScoreInfo<Reference2ObjectMap<Index, Array<SelectedInterval>>>>()
        val (documentOffset, matchOffset) = query.offset

        val defaultIndex = engine.indexMap[query.defaultIndex]
                ?: throw IllegalArgumentException("Index ${query.defaultIndex} not found")
        val defaultIndexInt = factory.fieldIndex(query.defaultIndex)

        val processed = engine.process(query.query, documentOffset, query.snippetCount, resultList)
        log.info("Processed $processed documents")

        val matched = mutableListOf<Match>()
        for ((i, result) in resultList.withIndex()) {
            val document = collection.document(result.document)
            val scores = getScoresForIndex(result, defaultIndex).let {
                if (i == 0) it.subList(matchOffset, it.size) else it
            }
            for ((j, score) in scores.withIndex()) {
                val reader = document.content(defaultIndexInt) as InputStreamReader
                val regex = """\s+"""
                val words = reader.readText().split(regex.toRegex())


                val (left, right) = score.interval
                val prefix = Math.max(left - 5, 0)
                val suffix = Math.min(right + 5, words.size)

                val prefixText = words.subList(prefix, left).joinToString(separator = " ")
                val matchedText = words.subList(left, right).joinToString(separator = " ")
                val suffixText = words.subList(right, suffix).joinToString(separator = " ")

                val match = Match(
                        collectionName,
                        result.document,
                        left,
                        right - left,
                        document.uri().toString(),
                        document.title().toString(),
                        Payload.Snippet.Json(AnnotatedText(
                                prefixText + matchedText + suffixText,
                                emptyMap(),
                                emptyList(),
                                listOf(QueryMapping(
                                        textIndex = MatchedRegion(prefixText.length, matchedText.length),
                                        queryIndex = MatchedRegion(0, query.query.length)
                                ))
                        )),
                        canExtend = prefix > 0 || suffix < words.size - 1
                )
                log.info("Found match $match")
                matched.add(match)
                if (matched.size >= query.snippetCount) {
                    return SearchResult(matched, Offset(result.document.toInt(), j + 1))
                }
            }
        }
        return SearchResult(matched, null)
    }
}


fun getScoresForIndex(
        result: DocumentScoreInfo<Reference2ObjectMap<Index, Array<SelectedInterval>>>,
        index: Index
) = result.info[index]?.toList() ?: throw IllegalArgumentException("No results for index $index")

operator fun Interval.component1() = left
operator fun Interval.component2() = right