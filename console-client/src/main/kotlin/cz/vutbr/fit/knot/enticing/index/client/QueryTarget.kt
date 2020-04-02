package cz.vutbr.fit.knot.enticing.index.client

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.query.processor.FuelQueryExecutor
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.flattenResults
import kotlinx.coroutines.runBlocking
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

private val apiBasePath = "/api/v1"

private val queryExecutor = FuelQueryExecutor("$apiBasePath/query")
private val queryDispatcher = QueryDispatcher(queryExecutor, ComponentType.CONSOLE_CLIENT, SimpleStdoutLoggerFactory)

@ExperimentalTime
sealed class QueryTarget(val name: String) {
    abstract fun submit(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>>
    abstract fun getAll(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>>

    data class WebserverTarget(val address: String, val settingsId: Int) : QueryTarget("Webserver") {

        private val api = WebserverApi(address, settingsId)

        override fun submit(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> = measureTimedValue {
            api.sendQuery(query).searchResults.map { it.toIndexServerFormat() }
        }

        override fun getAll(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> = measureTimedValue {
            var currentResult = api.sendQuery(query)
            val allResults = currentResult.searchResults.map { it.toIndexServerFormat() }.toMutableList()

            while (currentResult.hasMore) {
                currentResult = api.getMore()
                allResults.addAll(currentResult.searchResults.map { it.toIndexServerFormat() })
            }

            allResults
        }

    }

    data class QueryDispatcherTarget(val servers: List<String>) : QueryTarget("Dispatcher") {

        private val onResult: (RequestData<Map<CollectionName, Offset>>, MResult<IndexServer.IndexResultList>) -> Unit = { request, result ->
            if (result.isSuccess)
                println("Request $request finished, got ${result.value.searchResults.size} results")
            else println("Request $request failed")
        }

        override fun submit(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> {
            val nodes = servers.map { IndexServerRequestData(it) }
            return measureTimedValue {
                dispatch(query, nodes, onResult)
                        .first.searchResults
                        .map { it.toIndexServerFormat() }
            }
        }

        override fun getAll(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> = measureTimedValue {
            var nodes = servers.map { IndexServerRequestData(it) }
            var result = dispatch(query, nodes, onResult)
            val allResults = result.first.searchResults.map { it.toIndexServerFormat() }.toMutableList()

            while (result.second.isNotEmpty()) {
                nodes = result.second.map { (address, offset) -> IndexServerRequestData(address, offset) }
                result = dispatch(query, nodes, onResult)
                allResults.addAll(result.first.searchResults.map { it.toIndexServerFormat() })
            }

            allResults
        }

        private fun dispatch(query: SearchQuery, nodes: List<IndexServerRequestData>, onResult: (RequestData<Map<CollectionName, Offset>>, MResult<IndexServer.IndexResultList>) -> Unit) =
                queryDispatcher.dispatchQuery(query, nodes, onResult)
                        .flattenResults(query.query, SimpleStdoutLoggerFactory.namedLogger("QueryDispatcherTarget"))
    }

    data class IndexServerTarget(val address: String) : QueryTarget("IndexServer") {
        override fun submit(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> =
                runBlocking {
                    measureTimedValue { queryExecutor.invoke(query, IndexServerRequestData(address)).value.searchResults }
                }


        override fun getAll(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> = measureTimedValue {
            runBlocking {
                var currentResult = queryExecutor.invoke(query, IndexServerRequestData(address)).value
                val allResults = currentResult.searchResults.toMutableList()
                while (currentResult.offset != null && currentResult.offset!!.isNotEmpty()) {
                    currentResult = queryExecutor.invoke(query, IndexServerRequestData(address, currentResult.offset)).value
                    allResults.addAll(currentResult.searchResults)
                }
                allResults
            }
        }
    }
}