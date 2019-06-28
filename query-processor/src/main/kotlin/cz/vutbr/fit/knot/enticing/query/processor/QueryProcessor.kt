package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.response.SearchResult
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.query.processor.request.ServerInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

typealias ContactServer = (searchQuery: SearchQuery, serverInfo: ServerInfo) -> MResult<SearchResult>

fun process(searchQuery: SearchQuery, servers: List<ServerInfo>, contactServer: ContactServer): Map<String, List<MResult<SearchResult>>> = runBlocking {
    val serversToCall = servers.toMutableList()
    var collectedSnippetsCount = 0
    val serverResults = mutableMapOf<String, MutableList<MResult<SearchResult>>>()

    while (serversToCall.isNotEmpty() && collectedSnippetsCount < searchQuery.snippetCount) {
        val snippetsToCollect = searchQuery.snippetCount - collectedSnippetsCount
        val lastResults = serversToCall
                .mapIndexed { i, server -> splitSnippetCount(i, serversToCall.size, snippetsToCollect, searchQuery) to server }
                .map { (query, server) ->
                    async {
                        server.address to contactServer(query, server)
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




