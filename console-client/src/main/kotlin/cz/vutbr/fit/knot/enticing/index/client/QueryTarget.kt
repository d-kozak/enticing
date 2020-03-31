package cz.vutbr.fit.knot.enticing.index.client

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.query.processor.FuelQueryExecutor
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.flattenResults
import cz.vutbr.fit.knot.enticing.query.processor.fuel.jsonBody
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep
import java.util.*
import kotlin.concurrent.thread
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
            if (query.uuid != null) partialResultChecker(query.uuid!!)

            val address = "http://$address/api/v1/query?settings=$settingsId"
            return measureTimedValue {
                address.httpPost()
                        .jsonBody(query)
                        .responseString().third.get().toDto<WebServer.ResultList>()
                        .searchResults.map { it.toIndexServerFormat() }
            }
        }

        private fun partialResultChecker(uuid: UUID) {
            val address = "http://$address/api/v1/query/storage/$uuid"
            thread {
                sleep(500)
                try {
                    var cnt = 0
                    var results = address.httpGet()
                            .responseString().third.get()
                            .toDto<WebServer.ResultList>()

                    println("retrieved ${results.searchResults.size} eager results")
                    cnt += results.searchResults.size

                    while (results.hasMore) {
                        sleep(500)
                        results = address.httpGet()
                                .responseString().third.get()
                                .toDto()
                        println("retrieved ${results.searchResults.size} eager results")
                        cnt += results.searchResults.size
                    }

                    println("No more results, done, total count is $cnt")
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    data class QueryDispatcherTarget(val servers: List<String>) : QueryTarget("Dispatcher") {
        override fun query(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> {
            val nodes = servers.map { IndexServerRequestData(it) }
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

    data class IndexServerTarget(val address: String) : QueryTarget("IndexServer") {
        override fun query(query: SearchQuery): TimedValue<List<IndexServer.SearchResult>> =
                runBlocking {
                    measureTimedValue { queryExecutor.invoke(query, IndexServerRequestData(address)).value.searchResults }
                }


    }
}