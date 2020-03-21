package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.log.Logger
import cz.vutbr.fit.knot.enticing.log.error

fun Map<String, List<MResult<IndexServer.CollectionResultList>>>.flattenResults(): IndexServer.IndexResultList {
    val matched = mutableListOf<IndexServer.SearchResult>()
    val errors = mutableMapOf<CollectionName, ErrorMessage>()

    for ((collectionName, collectionResults) in this) {
        for (collectionResult in collectionResults) {
            if (collectionResult.isSuccess) {
                matched.addAll(collectionResult.value.searchResults)
            } else {
                errors[collectionName] = "${collectionResult.exception::class.simpleName}:${collectionResult.exception.message}"
                collectionResult.exception.printStackTrace()
            }
        }
    }

    val offset: Map<CollectionName, Offset> =
            this.map { (collectionName, collectionResults) ->
                        val last = collectionResults.lastOrNull() ?: return@map null
                        if (last.isSuccess)
                            last.value.offset?.let { collectionName to it }
                        else null
                    }.filterNotNull()
                    .toMap()

    return IndexServer.IndexResultList(matched, offset, errors)
}

fun Map<String, List<MResult<IndexServer.IndexResultList>>>.flattenResults(query: String, logger: Logger): Pair<WebServer.ResultList, MutableMap<String, Map<String, Offset>>> {
    val snippets = mutableListOf<WebServer.SearchResult>()
    val errors = mutableMapOf<ServerId, ErrorMessage>()

    val offset = mutableMapOf<String, Map<String, Offset>>()

    for ((serverId, results) in this) {
        for (serverResult in results) {
            if (serverResult.isSuccess) {
                snippets.addAll(
                        serverResult.value.searchResults.map { it.withHost(serverId) }
                )
                if (serverResult.value.offset.isNotEmpty())
                    offset[serverId] = serverResult.value.offset
                if (serverResult.value.errors.isNotEmpty()) {
                    val msg = serverResult.value.errors.toString()
                    errors[serverId] = msg
                    logger.warn("Server $serverId for query '$query' responded with $msg")
                }
            } else {
                val exception = serverResult.exception as QueryDispatcherException
                errors[serverId] = "${exception::class.simpleName}:${exception.message}"
                offset.remove(serverId)
                logger.error(exception)
            }
        }
    }

    return WebServer.ResultList(snippets, errors) to offset
}