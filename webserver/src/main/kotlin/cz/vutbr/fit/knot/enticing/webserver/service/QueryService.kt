package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.annotation.Temporary
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompilerException
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcherException
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
        private val indexServerConnector: IndexServerConnector,
        private val compilerService: EqlCompilerService
) {


    fun validateQuery(query: String, settings: Long) = compilerService.validateQuery(query, format(settings).toMetadataConfiguration())

    fun query(query: SearchQuery, selectedSettings: Long, session: HttpSession): WebServer.ResultList {
        val errors = validateQuery(query.query, selectedSettings).errors
        if (errors.isNotEmpty()) throw EqlCompilerException(errors.toString())
        val searchSettings = checkUserCanAccessSettings(selectedSettings)
        val requestData = searchSettings.servers.map { IndexServerRequestData(it) }
        log.info("Executing query $query with requestData $requestData")
        val (result, offset) = flatten(dispatcher.dispatchQuery(query, requestData))

        session.setAttribute("lastQuery", LastQuery(query, selectedSettings, offset))

        return result
    }

    fun getMore(session: HttpSession): WebServer.ResultList? {
        val (query, selectedSettings, offset) = session.getAttribute("lastQuery") as? LastQuery ?: return null
        val searchSettings = checkUserCanAccessSettings(selectedSettings)
        val requestData = searchSettings.servers
                .filter { it in offset && offset.getValue(it).isNotEmpty() }
                .map { IndexServerRequestData(it, offset[it]) }
        log.info("Executing query $offset with requestData $requestData")
        val (result, newOffset) = flatten(dispatcher.dispatchQuery(query, requestData))
        session.setAttribute("lastQuery", LastQuery(query, selectedSettings, newOffset))
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
                    try {
                        indexServerConnector.getFormat(server)
                    } catch (ex: Exception) {
                        log.warn("Could not contact server $server")
                        ex.printStackTrace()
                        null
                    }
                }
            }.awaitAll().filterNotNull()

            mergeCorpusFormats(formats)
        }
    }

    fun checkUserCanAccessSettings(selectedSettings: Long): SearchSettings {
        val currentUser = userService.currentUser

        val searchSettings = searchSettingsRepository.findById(selectedSettings).orElseThrow { IllegalArgumentException("Unknown searchSettings id $selectedSettings") }

        if (searchSettings.private && (currentUser == null || !currentUser.isAdmin)) {
            throw IllegalArgumentException("Settings is private, current user (or anonymous user) cannot use it")
        }
        return searchSettings
    }

    fun getRawDocument(request: WebServer.RawDocumentRequest): String = indexServerConnector.getRawDocument(request)

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
                if (serverResult.value.offset.isNotEmpty())
                    offset[serverId] = serverResult.value.offset
                if (serverResult.value.errors.isNotEmpty()) {
                    val msg = serverResult.value.errors.toString()
                    errors[serverId] = msg
                    log.warn("Server $serverId responded with $msg")
                }
            } else {
                val exception = serverResult.exception as QueryDispatcherException
                errors[serverId] = "${exception::class.simpleName}:${exception.message}"

                offset.remove(serverId)
                @Temporary("should be delegated to error logging service...when there is one...")
                exception.printStackTrace()
            }
        }
    }

    return WebServer.ResultList(snippets, errors) to offset
}