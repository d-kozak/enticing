package cz.vutbr.fit.knot.enticing.testing.index.server

import cz.vutbr.fit.knot.enticing.dto.CollectionRequestData
import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.Offset
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CollectionManagerConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.metadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.index.collection.manager.CollectionQueryExecutor
import cz.vutbr.fit.knot.enticing.index.mg4j.initMg4jCollectionManager
import cz.vutbr.fit.knot.enticing.index.startIndexing
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.flattenResults
import java.io.File


private val metadata = metadataConfiguration {
    indexes {
        "position" whichIs "Position of the word in the document"
        "token" whichIs "Original word in the document"
        "tag" whichIs "tag"
        "lemma" whichIs "Lemma of the word"
        "parpos" whichIs "parpos"
        "function" whichIs "function"
        "parwrod" whichIs "parword"
        "parlemma" whichIs "parlemma"
        "paroffset" whichIs "paroffset"
        "link" whichIs "link"
        "length" whichIs "length"
        "docuri" whichIs "docuri"
        "lower" whichIs "lower"
        "nerid" whichIs "nerid"
        "nertag" whichIs "nertag"
        attributeIndexes(10)
        "nertype" whichIs "nertype"
        "nerlength" whichIs "nerlength"
        "_glue" whichIs "Contains information whether words in the default index should be separated by spaces or not"
    }

    entities {
        "person" with attributes("url", "image", "name", "gender", "birthplace", "birthdate", "deathplace", "deathdate", "profession", "nationality")

        "artist" with attributes("url", "image", "name", "gender", "birthplace", "birthdate", "deathplace", "deathdate", "role", "nationality")

        "location" with attributes("url", "image", "name", "country")

        "artwork" with attributes("url", "image", "name", "form", "datebegun", "datecompleted", "movement", "genre", "author")

        "event" with attributes("url", "image", "name", "startdate", "enddate", "location")

        "museum" with attributes("url", "image", "name", "type", "established", "director", "location")

        "family" with attributes("url", "image", "name", "role", "nationality", "members")

        "group" with attributes("url", "image", "name", "role", "nationality")

        "nationality" with attributes("url", "image", "name", "country")

        "date" with attributes("url", "image", "year", "month", "day")

        "interval" with attributes("url", "image", "fromyear", "frommonth", "fromday", "toyear", "tomonth", "today")

        "form" with attributes("url", "image", "name")

        "medium" with attributes("url", "image", "name")

        "mythology" with attributes("url", "image", "name")

        "movement" with attributes("url", "image", "name")

        "genre" with attributes("url", "image", "name")

        extraAttributes("nertype", "nerlength")
    }

}.also {
    it.validateOrFail()
}


private val collections = listOf("col1", "col2", "col3")

private val templateBuilderConfig = IndexBuilderConfig(
        "CC",
        "col1",
        File("../data/pagination/mg4j"),
        File("../data/pagination/indexed"),
        metadata
)

private val templateCollectionManagerConfig = CollectionManagerConfiguration(
        "CC",
        "col1",
        File("../data/pagination/mg4j"),
        File("../data/pagination/indexed"),
        metadata
)

fun indexAll() {
    for (col in collections)
        startIndexing(templateBuilderConfig.copy(collectionName = col, mg4jDir = File("../data/integration/$col/mg4j"), indexDir = File("../data/integration/$col/indexed")), SimpleStdoutLoggerFactory)
}

class TestQueryService {
    val collectionManagers = collections.map { templateCollectionManagerConfig.copy(collectionName = it, mg4jDir = File("../data/integration/$it/mg4j"), indexDir = File("../data/integration/$it/indexed")) }
            .map { initMg4jCollectionManager(it, SimpleStdoutLoggerFactory) }
            .associateBy { it.collectionName }

    val queryDispatcher = QueryDispatcher(CollectionQueryExecutor(collectionManagers), ComponentType.INDEX_SERVER, SimpleStdoutLoggerFactory)

    private val logger = SimpleStdoutLoggerFactory.logger { }

    private val eqlCompiler = EqlCompiler(SimpleStdoutLoggerFactory)

    fun processQuery(query: SearchQuery): IndexServer.IndexResultList {
        query.eqlAst = eqlCompiler.parseOrFail(query.query, metadata)

        val requestData = if (query.offset != null) query.offset!!.map { (collection, offset) -> CollectionRequestData(collection, offset) }
        else collectionManagers.keys.map { CollectionRequestData(it, Offset(0, 0)) }

        logger.info("Executing query $query with requestData $requestData")
        return queryDispatcher.dispatchQuery(query, requestData)
                .flattenResults()
    }

}

fun prepareTestQueryService(): TestQueryService = TestQueryService()


