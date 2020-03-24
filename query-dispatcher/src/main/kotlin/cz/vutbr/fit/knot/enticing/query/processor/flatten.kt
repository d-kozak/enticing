package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.log.Logger
import cz.vutbr.fit.knot.enticing.log.error

fun Map<String, List<MResult<IndexServer.CollectionResultList>>>.flattenResults(): IndexServer.IndexResultList {
    val matched = mutableListOf<IndexServer.SearchResult>()
    val errors = mutableMapOf<CollectionName, ErrorMessage>()

    val offset = mutableMapOf<CollectionName, Offset>()

    for ((collectionName, collectionResults) in this) {
        for (collectionResult in collectionResults) {
            if (collectionResult.isSuccess) {
                val value = collectionResult.value
                matched.addAll(value.searchResults)
                if (value.offset != null)
                    offset[collectionName] = value.offset!!
                else offset.remove(collectionName)
            } else {
                offset.remove(collectionName)
                errors[collectionName] = "${collectionResult.exception::class.simpleName}:${collectionResult.exception.message}"
                collectionResult.exception.printStackTrace()
            }
        }
    }

    return IndexServer.IndexResultList(matched, if (offset.isNotEmpty()) offset else null, errors)
}

fun Map<String, List<MResult<IndexServer.IndexResultList>>>.flattenResults(query: String, logger: Logger): Pair<WebServer.ResultList, MutableMap<String, Map<String, Offset>>> {
    val snippets = mutableListOf<WebServer.SearchResult>()
    val errors = mutableMapOf<ServerId, ErrorMessage>()

    val offset = mutableMapOf<String, Map<String, Offset>>()

    for ((serverId, results) in this) {
        for (serverResult in results) {
            if (serverResult.isSuccess) {
                snippets.addAll(serverResult.value.searchResults.map { it.withHost(serverId) })
                val serverOffset = serverResult.value.offset
                if (serverOffset != null && serverOffset.isNotEmpty())
                    offset[serverId] = serverOffset
                else offset.remove(serverId)

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

    return WebServer.ResultList(snippets, errors, hasMore = offset.isNotEmpty()) to offset
}