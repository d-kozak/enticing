package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.annotation.Temporary
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.webserver.dto.LastQuery
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.servlet.http.HttpSession

private val log = LoggerFactory.getLogger(QueryService::class.java)

@Service
class QueryService(
        private val dispatcher: QueryDispatcher<SearchQuery, Map<CollectionName, Offset>, IndexServer.IndexResultList>,
        private val searchSettingsRepository: SearchSettingsRepository,
        private val userService: EnticingUserService,
        private val indexServerConnector: IndexServerConnector
) {

    fun query(query: SearchQuery, selectedSettings: Long, session: HttpSession): WebServer.ResultList {
        val searchSettings = checkUserCanAccessSettings(selectedSettings)
        val requestData = searchSettings.servers.map { IndexServerRequestData(it) }
        log.info("Executing query $query with requestData $requestData")
        val (result, offset) = flatten(dispatcher.dispatchQuery(query, requestData))

        session.setAttribute("lastQuery", LastQuery(query, offset))

        return result
    }

    fun getMore(selectedSettings: Long, session: HttpSession): WebServer.ResultList? {
        val (query, offset) = session.getAttribute("lastQuery") as? LastQuery ?: return null
        val searchSettings = checkUserCanAccessSettings(selectedSettings)
        val requestData = searchSettings.servers.map { IndexServerRequestData(it, offset[it]) }
        log.info("Executing query $offset with requestData $requestData")
        val (result, newOffset) = flatten(dispatcher.dispatchQuery(query, requestData))
        session.setAttribute("lastQuery", LastQuery(query, newOffset))
        return result
    }

    fun context(query: WebServer.ContextExtensionQuery): SnippetExtension = indexServerConnector.contextExtension(query)

    fun document(query: WebServer.DocumentQuery): WebServer.FullDocument {
        val indexDocument = indexServerConnector.getDocument(query)
        return indexDocument.toWebserverFormat(query.host, query.collection, query.documentId, query.query)
    }

    fun format(selectedSettings: Long): CorpusFormat {
        val searchSettings = checkUserCanAccessSettings(selectedSettings)

        require(searchSettings.servers.isNotEmpty()) { "Search settings $searchSettings has no associated servers, therefore it has no CorpusFormat" }

        return runBlocking {
            val formats = searchSettings.servers.map { server ->
                async {
                    indexServerConnector.getFormat(server)
                }
            }.awaitAll()

            mergeCorpusFormats(formats)
        }
    }

    private fun checkUserCanAccessSettings(selectedSettings: Long): SearchSettings {
        val currentUser = userService.currentUser

        val searchSettings = searchSettingsRepository.findById(selectedSettings).orElseThrow { IllegalArgumentException("Unknown searchSettings id $selectedSettings") }

        if (searchSettings.private && (currentUser == null || !currentUser.isAdmin)) {
            throw IllegalArgumentException("Settings is private, current user (or anonymous user) cannot use it")
        }
        return searchSettings
    }
}

fun flatten(result: Map<String, List<MResult<IndexServer.IndexResultList>>>): Pair<WebServer.ResultList, MutableMap<String, Map<String, Offset>>> {
    val snippets = mutableListOf<WebServer.SearchResult>()
    val errors = mutableMapOf<ServerId, ErrorMessage>()

    val offset = mutableMapOf<String, Map<String, Offset>>()

    for ((serverId, results) in result) {
        for (serverResult in results) {
            if (serverResult.isSuccess) {
                snippets.addAll(
                        serverResult.value.searchResults.map { it.withHost(serverId) }
                )
                offset[serverId] = serverResult.value.offset
                if (serverResult.value.errors.isNotEmpty()) {
                    val msg = serverResult.value.errors.toString()
                    errors[serverId] = msg
                    log.warn("Server $serverId responded with $msg")
                }
            } else {
                val exception = serverResult.exception
                errors[serverId] = "${exception::class.simpleName}:${exception.message}"

                @Temporary("should be delegated to error logging service...when there is one...")
                exception.printStackTrace()
            }
        }
    }

    return WebServer.ResultList(snippets, errors) to offset
}