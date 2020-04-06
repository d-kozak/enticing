package cz.vutbr.fit.knot.enticing.webserver.service


import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap


@Service
class TemporaryResultStorage(loggerFactory: LoggerFactory) {

    private data class StorageEntry(
            private val result: WebServer.EagerSearchResult
    ) {
        private var modifiedAt: Long = System.currentTimeMillis()

        fun <T> access(block: (WebServer.EagerSearchResult) -> T): T = synchronized(this) {
            modifiedAt = System.currentTimeMillis()
            block(result)
        }

        val isStale: Boolean
            get() = System.currentTimeMillis() - modifiedAt > 30_000

    }

    private val memory = ConcurrentHashMap<String, StorageEntry>()

    private val logger = loggerFactory.logger { }

    fun <T> useEntryFor(uuid: UUID?, block: () -> T): T {
        if (uuid == null) return block()
        initEntry(uuid.toString())
        return try {
            block()
        } finally {
            markDone(uuid.toString())
        }
    }

    fun callbackFor(uuid: UUID?): ((RequestData<Map<CollectionName, Offset>>, MResult<IndexServer.IndexResultList>) -> Unit)? {
        if (uuid == null) return null
        return { request, result ->
            if (result.isSuccess) {
                val data = result.value.searchResults.map { it.withHost(request.address) }
                this.addResult(uuid.toString(), data)
            }
        }
    }


    fun addResult(id: String, resultList: List<WebServer.SearchResult>) {
        val entry = memory[id]
        if (entry != null) {
            entry.access { result ->
                result.searchResults.addAll(resultList)
            }
        } else memory[id] = StorageEntry(WebServer.EagerSearchResult(WebServer.SearchingState.RUNNING, resultList.toMutableList()))
    }

    fun getResults(id: String): WebServer.EagerSearchResult {
        val entry = memory[id] ?: return WebServer.EagerSearchResult(WebServer.SearchingState.NONE)
        return entry.access { result ->
            if (result.state == WebServer.SearchingState.FINISHED) memory.remove(id)
            val searchResults = result.searchResults.toMutableList()
            result.searchResults.clear()
            WebServer.EagerSearchResult(result.state, searchResults)
        }
    }

    private fun initEntry(id: String) {
        memory[id] = StorageEntry(WebServer.EagerSearchResult(WebServer.SearchingState.RUNNING))
    }

    private fun markDone(id: String) {
        val entry = memory[id]
        if (entry != null) {
            entry.access { result ->
                result.state = WebServer.SearchingState.FINISHED
            }
        } else memory[id] = StorageEntry(WebServer.EagerSearchResult(WebServer.SearchingState.FINISHED))
    }


    @Scheduled(fixedDelay = 5000)
    fun clearOldEntries() {
        logger.debug("Removing old entries")
        var count = 0
        memory.values.removeIf {
            val old = it.isStale
            if (old) count++
            old
        }

        logger.debug("Removed $count entries")
    }

}