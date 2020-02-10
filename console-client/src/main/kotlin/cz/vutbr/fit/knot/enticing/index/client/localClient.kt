package cz.vutbr.fit.knot.enticing.index.client

//
//import cz.vutbr.fit.knot.enticing.dto.ResultFormat
//import cz.vutbr.fit.knot.enticing.dto.SearchQuery
//import cz.vutbr.fit.knot.enticing.dto.TextFormat
//import cz.vutbr.fit.knot.enticing.dto.TextMetadata
//import cz.vutbr.fit.knot.enticing.dto.config.SearchConfig
//import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientType
//import cz.vutbr.fit.knot.enticing.index.collection.manager.CollectionManager
//import cz.vutbr.fit.knot.enticing.index.mg4j.initMg4jCollectionManager
//
//fun startLocalClient(config: ConsoleClientType.LocalIndex, searchConfig: SearchConfig, input: Sequence<String>) {
//    val queryExecutor = initMg4jCollectionManager(config.indexClientConfig.corpusConfiguration, config.indexClientConfig.collections[0])
//
//    for (line in input) {
//        executeLine(queryExecutor, line)
//    }
//}
//
//internal fun executeLine(collectionManager: CollectionManager, input: String) {
//    val query = SearchQuery(
//            input,
//            33,
//            null,
//            TextMetadata.Predefined("all"),
//            ResultFormat.SNIPPET,
//            TextFormat.TEXT_UNIT_LIST
//    )
//    try {
//        val response = collectionManager.query(query)
//        println(response)
//    } catch (ex: Exception) {
//        ex.printStackTrace()
//    }
//}
//
//
