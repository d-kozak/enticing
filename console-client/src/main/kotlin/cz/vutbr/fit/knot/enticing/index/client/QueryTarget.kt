package cz.vutbr.fit.knot.enticing.index.client

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.query.processor.FuelQueryExecutor
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.flattenResults
import kotlinx.coroutines.runBlocking
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

/**
 * Base path of the APIs
 */
@Cleanup("Should be somehow loaded from config")
private val apiBasePath = "/api/v1"

/**
 * query executor to use for submitting query to a remote IndexServer
 */
private val queryExecutor = FuelQueryExecutor("$apiBasePath/query")

/**
 * QueryDispatcher to dispatch queries to multiple IndexServers evenly
 */
private val queryDispatcher = QueryDispatcher(queryExecutor, ComponentType.CONSOLE_CLIENT, SimpleStdoutLoggerFactory)

/**
 * Base class representing a target for a query, which can be
 * 1) a webserver,
 * 2) a group of index servers
 * 3) a single index server
 */
@ExperimentalTime
sealed class QueryTarget(val name: String) {
    /**
     * Submit given query and wait for a result
     */
    abstract fun submitQuery(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>>

    /**
     * Collect all results for a given query
     */
    abstract fun collectAllResults(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>>


    /**
     * Submits requests to a webserver
     */
    class WebserverTarget(val address: String, val settingsId: Int, loggerFactory: LoggerFactory) : QueryTarget("Webserver") {

        private val api = WebserverApi(address, settingsId)

        override fun submitQuery(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> = measureTimedValue {
            api.sendQuery(query).searchResults.map { it.toIndexServerFormat() }
        }

        override fun collectAllResults(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> = measureTimedValue {
            var currentResult = api.sendQuery(query)
            currentResult.errors.forEach { System.err.println(it) }
            val allResults = currentResult.searchResults.map { it.toIndexServerFormat() }.toMutableList()

            while (currentResult.hasMore) {
                currentResult = api.getMore()
                currentResult.errors.forEach { System.err.println(it) }
                allResults.addAll(currentResult.searchResults.map { it.toIndexServerFormat() })
            }

            allResults
        }

    }

    /**
     * Submits requests to a group of index servers
     */
    class QueryDispatcherTarget(val servers: List<String>, loggerFactory: LoggerFactory) : QueryTarget("Dispatcher") {

        private val onResult: (RequestData<Map<CollectionName, Offset>>, MResult<IndexServer.IndexResultList>) -> Unit = { request, result ->
            if (result.isSuccess)
                println("Request $request finished, got ${result.value.searchResults.size} results")
            else println("Request $request failed")
        }

        override fun submitQuery(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> {
            val nodes = servers.map { IndexServerRequestData(it) }
            return measureTimedValue {
                dispatch(query, nodes, onResult)
                        .first.searchResults
                        .map { it.toIndexServerFormat() }
            }
        }

        override fun collectAllResults(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> = measureTimedValue {
            var nodes = servers.map { IndexServerRequestData(it) }
            var result = dispatch(query, nodes, onResult)
            result.first.errors.forEach { System.err.println(it) }
            val allResults = result.first.searchResults.map { it.toIndexServerFormat() }.toMutableList()

            while (result.second.isNotEmpty()) {
                nodes = result.second.map { (address, offset) -> IndexServerRequestData(address, offset) }
                result = dispatch(query, nodes, onResult)
                result.first.errors.forEach { System.err.println(it) }
                allResults.addAll(result.first.searchResults.map { it.toIndexServerFormat() })
            }

            allResults
        }

        private fun dispatch(query: SearchQuery, nodes: List<IndexServerRequestData>, onResult: (RequestData<Map<CollectionName, Offset>>, MResult<IndexServer.IndexResultList>) -> Unit) =
                queryDispatcher.dispatchQuery(query, nodes, onResult)
                        .flattenResults(query.query, SimpleStdoutLoggerFactory.namedLogger("QueryDispatcherTarget"))
    }

    /**
     * Submits requests to a single index server
     */
    class IndexServerTarget(val address: String, loggerFactory: LoggerFactory) : QueryTarget("IndexServer") {

        private val logger = loggerFactory.logger { }

        override fun submitQuery(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> =
                runBlocking {
                    measureTimedValue { queryExecutor.invoke(query, IndexServerRequestData(address)).unwrap().searchResults }
                }


        override fun collectAllResults(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> = measureTimedValue {
            runBlocking {
                var cnt = 1
                var currentResult = queryExecutor.invoke(query, IndexServerRequestData(address)).unwrap()
                currentResult.errors.forEach { System.err.println(it) }
                val allResults = currentResult.searchResults.toMutableList()
                logger.info("Server $address , Iteration ${cnt++}: got ${currentResult.searchResults.size} results")
                while (currentResult.offset != null && currentResult.offset!!.isNotEmpty()) {
                    currentResult = queryExecutor.invoke(query, IndexServerRequestData(address, currentResult.offset)).unwrap()
                    currentResult.errors.forEach { System.err.println(it) }
                    logger.info("""Server $address , Iteration ${cnt++}: got ${currentResult.searchResults.size} results
                        | Last result id was ${currentResult.searchResults.lastOrNull()?.uniqueId}
                    """.trimMargin())
                    allResults.addAll(currentResult.searchResults)
                }
                allResults
            }
        }
    }
}