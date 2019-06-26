package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.response.SearchResult
import cz.vutbr.fit.knot.enticing.query.processor.request.ServerInfo
import cz.vutbr.fit.knot.enticing.query.processor.request.ServerResult
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking


fun process(searchQuery: SearchQuery, serverInfo: List<ServerInfo>): List<ServerResult> = runBlocking {
    var servers = serverInfo.toMutableList()

    val snippetCount = 0
    val result = mutableListOf<ServerResult>()

    while (servers.isNotEmpty() && snippetCount < searchQuery.snippetCount) {
        val results = serverInfo.map {
            async {
                it.address to singleRequest(searchQuery, it)
            }
        }.awaitAll()

        servers.clear()
        for ((server, serverResult) in results) {

        }
    }




    result
}

fun singleRequest(searchQuery: SearchQuery, serverInfo: ServerInfo): SearchResult {
    TODO("Not implemented yet")
}



