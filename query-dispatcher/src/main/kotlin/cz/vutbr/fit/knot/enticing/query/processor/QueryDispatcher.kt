package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking




class QueryDispatcher<T: Query<T>,NodeInfo : RequestData,Result:QueryResult>(
        private val requestDispatcher: RequestDispatcher<T,NodeInfo,Result>
) {

    fun dispatchQuery(searchQuery: T, servers: List<NodeInfo>): Map<String, List<MResult<Result>>> = runBlocking {
        val serversToCall = servers.toMutableList()
        var collectedSnippetsCount = 0
        val serverResults = mutableMapOf<String, MutableList<MResult<Result>>>()

        while (serversToCall.isNotEmpty() && collectedSnippetsCount < searchQuery.snippetCount) {
            val snippetsToCollect = searchQuery.snippetCount - collectedSnippetsCount
            val lastResults = serversToCall
                    .mapIndexed { i, server -> splitSnippetCount(i, serversToCall.size, snippetsToCollect, searchQuery) to server }
                    .map { (query, server) ->
                        async {
                            server.address to requestDispatcher(query, server)
                        }
                    }.awaitAll()

            serversToCall.clear()
            for ((server, result) in lastResults) {
                val resultsPerServer = serverResults[server] ?: mutableListOf()
                if (result.isSuccess && result.value.matched.isNotEmpty()) {
                    if (result.value.offset.isNotEmpty())
                        serversToCall.add(requestDispatcher.createRequestData(server, result.value.offset))
                    collectedSnippetsCount += result.value.matched.size
                }
                resultsPerServer.add(result)
                serverResults[server] = resultsPerServer
            }
        }
        serverResults
    }

}


fun <T:Query<T>>splitSnippetCount(i: Int, serverCount: Int, wantedSnippets: Int, query: T): T {
    var newSnippetCount = wantedSnippets / serverCount
    val modulo = wantedSnippets % serverCount
    if (modulo != 0 && i < modulo) {
        newSnippetCount++
    }
    return query.updateSnippetCount(newSnippetCount)
}




