package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CollectionManagerConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.index.startIndexing
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import it.unimi.di.big.mg4j.query.QueryEngine
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import java.io.File


fun startNativeMg4jQuerySystem(conf: CollectionManagerConfiguration, buildFirst: Boolean = true) {
    if (buildFirst) {
        val builderConf = IndexBuilderConfig(conf.corpusName, conf.collectionName, conf.mg4jDir, conf.indexDir, conf.metadataConfiguration)
        startIndexing(builderConf, SimpleStdoutLoggerFactory)
    }
    val engine = initMg4jQueryEngine(conf)
    run(engine)
}


fun main() {
    startWithRegressionData()
}

fun startWithRegressionData() {
    val conf = CollectionManagerConfiguration("CC",
            "col1",
            File("./data/regres"),
            File("./data/regres/indexed"),
            withInheritanceMetadataConfig)
    startNativeMg4jQuerySystem(conf)
}

fun startNormalTestData() {
    val conf = CollectionManagerConfiguration("CC",
            "col1",
            File("./data/mg4j"),
            File("./data/indexed"),
            fullTestMetadataConfig)
    startNativeMg4jQuerySystem(conf)
}

private fun run(engine: QueryEngine) {
    val tokenIndex = engine.indexMap.getValue("token")
    println("==============")
    println("Engine ready")
    println("==============")
    do {
        print("?>")
        val query = readLine()!!

        val resultList = ObjectArrayList<Mg4jSearchResult>()

        try {
            engine.process(query, 0, 20, resultList)

            for (res in resultList) {
                val match = res.info[tokenIndex]!!.map { it.interval }
                val min = match.minBy { it.left }!!.left
                val max = match.maxBy { it.right }!!.right
                println("doc ${res.document} match at: [$min..$max]")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        resultList.clear()
    } while (query != ":q")
}