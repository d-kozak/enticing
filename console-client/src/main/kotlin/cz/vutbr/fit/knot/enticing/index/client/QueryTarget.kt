package cz.vutbr.fit.knot.enticing.index.client

import com.github.kittinunf.fuel.httpPost
import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.query.processor.FuelQueryExecutor
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.flattenResults
import cz.vutbr.fit.knot.enticing.query.processor.fuel.jsonBody
import kotlinx.coroutines.runBlocking
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

private val apiBasePath = "/api/v1"

private val queryExecutor = FuelQueryExecutor("$apiBasePath/query")
private val queryDispatcher = QueryDispatcher(queryExecutor, ComponentType.CONSOLE_CLIENT, SimpleStdoutLoggerFactory)

@ExperimentalTime
sealed class QueryTarget(val name: String) {
    abstract fun query(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>>

    data class WebserverTarget(val address: String, val settingsId: Int) : QueryTarget("Webserver") {

        override fun query(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> {
            val address = "http://$address/api/v1/query?settings=$settingsId"
            return measureTimedValue {
                address.httpPost()
                        .jsonBody(query)
                        .responseString().third.get().toDto<WebServer.ResultList>()
                        .searchResults.map { it.toIndexServerFormat() }
            }
        }
    }

    data class QueryDispatcherTarget(val corpus: CorpusConfiguration) : QueryTarget("Dispatcher") {
        override fun query(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> {
            val nodes = corpus.indexServers.map { IndexServerRequestData(it.fullAddress) }
            val onResult: (RequestData<Map<CollectionName, Offset>>, MResult<IndexServer.IndexResultList>) -> Unit = { request, result ->
                if (result.isSuccess)
                    println("Request $request finished, got ${result.value.searchResults.size} results")
                else println("Request $request failed")
            }
            return measureTimedValue {
                queryDispatcher.dispatchQuery(query, nodes, onResult)
                        .flattenResults(query.query, SimpleStdoutLoggerFactory.namedLogger("QueryDispatcherTarget"))
                        .first.searchResults
                        .map { it.toIndexServerFormat() }
            }
        }
    }

    class IndexServerTarget(val address: String) : QueryTarget("IndexServer") {
        override fun query(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> =
                runBlocking {
                    measureTimedValue { queryExecutor.invoke(query, IndexServerRequestData(address)).value.searchResults }
                }


    }
}