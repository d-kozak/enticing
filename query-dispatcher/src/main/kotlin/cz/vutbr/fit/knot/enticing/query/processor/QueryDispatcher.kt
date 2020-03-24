package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.Query
import cz.vutbr.fit.knot.enticing.dto.QueryResult
import cz.vutbr.fit.knot.enticing.dto.RequestData
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.log.measure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlin.math.max
import kotlin.math.min


/**
 * Performs the dispatcher algorithm described in @see <a href="../../../../../../../../../../../documentation/dispatcher.md">documentation</a>
 *
 * It's responsibility is to send the requests to multiple nodes and retry if necessary (and possible aka if they haven't failed or run out before). The execution is delegated to RequestDispatcher.
 * To allow the RequestDispatchers to work with specific data types, this class is generic, upper bounds are used to express the minimal
 * requirements that QueryDispatcher needs.
 *
 * @param <QueryType> query to be executed
 * @param <OffsetType> offset representation, QueryDispatcher does not need to understand it, but it will check for null value to
 * determine whether subsequent queries are possible
 * @param <Result> result of the query execution
 *
 **/
class QueryDispatcher<QueryType : Query<QueryType>, OffsetType, Result : QueryResult<OffsetType>>(

        /**
         * The actual execution of the query is handled by it.
         */
        private val queryExecutor: QueryExecutor<QueryType, OffsetType, Result>,
        val componentType: ComponentType,
        loggerFactory: LoggerFactory
) {

    val logger = loggerFactory.logger { }

    /**
     * Dispatches query onto a list of node, collects the results and retries if necessary and possible.
     */
    fun dispatchQuery(searchQuery: QueryType, nodes: List<RequestData<OffsetType>>, onResult: ((RequestData<OffsetType>, MResult<Result>) -> Unit)? = null): Map<String, List<MResult<Result>>> = logger.measure("DispatchQuery-$componentType", "query='${searchQuery.query}', snippetCount=${searchQuery.snippetCount}, nodes=${nodes.map { it.address }}") {
        runBlocking(context = Dispatchers.IO) {
            val serversToCall = nodes.toMutableList()
            var collectedSnippetsCount = 0
            val serverResults = mutableMapOf<String, MutableList<MResult<Result>>>()

            while (serversToCall.isNotEmpty() && collectedSnippetsCount < searchQuery.snippetCount) {
                val snippetsToCollect = searchQuery.snippetCount - collectedSnippetsCount
                val lastResults = serversToCall
                        .mapIndexed { i, server -> splitSnippetCount(i, serversToCall.size, snippetsToCollect, searchQuery) to server }
                        .map { (query, server) ->
                            async {
                                val result = queryExecutor(query, server)
                                onResult?.invoke(server, result)
                                server.address to result
                            }
                        }.awaitAll()
                serversToCall.clear()
                for ((server, result) in lastResults) {
                    val resultsPerServer = serverResults[server] ?: mutableListOf()
                    if (result.isSuccess && result.value.offset != null) {
                        serversToCall.add(result.value.createRequest(server))
                        collectedSnippetsCount += result.value.searchResults.size
                    }
                    resultsPerServer.add(result)
                    serverResults[server] = resultsPerServer
                }
            }
            message = "Collected $collectedSnippetsCount snippets"
            serverResults
        }
    }
}


/**
 * Serves to split snippets evenly between servers.
 * The original snippet count is reduced according to how many servers the query will be sent to.
 * @param i index if server for which the query is made
 * @param serverCount number of servers
 * @param wantedSnippets number of snippets wanted
 * @param query query to be executed
 * @return updated query with reduced amount of snippets
 */
internal fun <QueryType : Query<QueryType>> splitSnippetCount(i: Int, serverCount: Int, wantedSnippets: Int, query: QueryType): QueryType {
    var newSnippetCount = wantedSnippets / serverCount
    val modulo = wantedSnippets % serverCount
    if (modulo != 0 && i < modulo) {
        newSnippetCount++
    }
    return query.updateSnippetCount(min(max(3, newSnippetCount), 10_000))
}




