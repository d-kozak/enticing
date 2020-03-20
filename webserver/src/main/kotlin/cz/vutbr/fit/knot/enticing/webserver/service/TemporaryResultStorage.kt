package cz.vutbr.fit.knot.enticing.webserver.service


import cz.vutbr.fit.knot.enticing.dto.WebServer
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class TemporaryResultStorage(loggerFactory: LoggerFactory) {

    private data class StorageEntry(
            var resultList: WebServer.ResultList,
            var modifiedAt: Long = System.currentTimeMillis()
    )

    private val memory = ConcurrentHashMap<String, StorageEntry>()

    private val logger = loggerFactory.logger { }


    fun addResult(id: String, resultList: List<WebServer.SearchResult>) {
        val entry = memory[id]
        if (entry != null) {
            synchronized(entry) {
                entry.resultList = WebServer.ResultList(entry.resultList.searchResults + resultList, entry.resultList.errors)
                entry.modifiedAt = System.currentTimeMillis()
            }
        } else memory[id] = StorageEntry(WebServer.ResultList(resultList))
    }

    fun getResults(id: String) = memory.remove(id)?.resultList

    fun markDone(id: String) {
        val entry = memory[id]
        if (entry != null) {
            synchronized(entry) {
                entry.modifiedAt = System.currentTimeMillis()
                entry.resultList.hasMore = false
            }
        } else memory[id] = StorageEntry(WebServer.ResultList(emptyList(), hasMore = false))
    }


    @Scheduled(fixedDelay = 5000)
    fun clearOldEntries() {
        logger.debug("Removing old entries")
        var count = 0
        memory.values.removeIf {
            val old = System.currentTimeMillis() - it.modifiedAt > 30_000
            if (old) count++
            old
        }

        logger.debug("Removed $count entries")
    }

}