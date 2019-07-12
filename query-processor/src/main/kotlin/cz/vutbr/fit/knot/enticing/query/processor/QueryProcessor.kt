package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.ServerInfo
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

fun process(searchQuery: SearchQuery, servers: List<ServerInfo>, requestDispatcher: RequestDispatcher): Map<String, List<MResult<IndexServer.SearchResult>>> = runBlocking {
    val serversToCall = servers.toMutableList()
    var collectedSnippetsCount = 0
    val serverResults = mutableMapOf<String, MutableList<MResult<IndexServer.SearchResult>>>()

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
                if (result.value.offset != null)
                    serversToCall.add(ServerInfo(server, result.value.offset!!))
                collectedSnippetsCount += result.value.matched.size
            }
            resultsPerServer.add(result)
            serverResults[server] = resultsPerServer
        }
    }
    serverResults
}

fun splitSnippetCount(i: Int, serverCount: Int, wantedSnippets: Int, query: SearchQuery): SearchQuery {
    var newSnippetCount = wantedSnippets / serverCount
    val modulo = wantedSnippets % serverCount
    if (modulo != 0 && i < modulo) {
        newSnippetCount++
    }
    return query.copy(snippetCount = newSnippetCount)
}




