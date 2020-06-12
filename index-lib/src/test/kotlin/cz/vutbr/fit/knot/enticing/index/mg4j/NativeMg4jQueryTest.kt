package cz.vutbr.fit.knot.enticing.index.mg4j

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CollectionManagerConfiguration
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import java.io.File

private val conf = CollectionManagerConfiguration("CC",
        "col1",
        File("./data/mg4j"),
        File("./data/indexed"),
        fullTestMetadataConfig)

private val engine = initMg4jQueryEngine(conf)

private val tokenIndex = engine.indexMap.getValue("token")

fun main() {
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
                println("res")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        resultList.clear()
    } while (query != ":q")
}