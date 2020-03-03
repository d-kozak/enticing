package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.error
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.log.measure
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcherException
import cz.vutbr.fit.knot.enticing.webserver.dto.LastQuery
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.exception.InvalidSearchSettingsException
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import org.springframework.stereotype.Service
import javax.servlet.http.HttpSession

@Service
class QueryService(
        private val dispatcher: QueryDispatcher<SearchQuery, Map<CollectionName, Offset>, IndexServer.IndexResultList>,
        private val searchSettingsRepository: SearchSettingsRepository,
        private val userHolder: CurrentUserHolder,
        private val indexServerConnector: IndexServerConnector,
        private val compilerService: EqlCompilerService,
        private val corpusFormatService: CorpusFormatService,
        loggerFactory: LoggerFactory
) {

    val logger = loggerFactory.logger { }

    fun validateQuery(query: String, settings: Long) = logger.measure("validateQuery", "query='$query',settingsId=$settings") {
        compilerService.validateQuery(query, format(settings).toMetadataConfiguration())
    }

    fun format(settings: Long) = corpusFormatService.loadFormat(checkUserCanAccessSettings(settings))

    fun query(query: SearchQuery, selectedSettings: Long, session: HttpSession): WebServer.ResultList = logger.measure("query", "query='${query.query}',settingsId=$selectedSettings") {
        val searchSettings = checkUserCanAccessSettings(selectedSettings)
        compilerService.validateOrFail(query.query, corpusFormatService.loadFormat(searchSettings).toMetadataConfiguration())
        val requestData = searchSettings.servers.map { IndexServerRequestData(it) }
        logger.info("Executing query $query with requestData $requestData")
        val (result, offset) = flatten(query.query, dispatcher.dispatchQuery(query, requestData))

        session.setAttribute("lastQuery", LastQuery(query, selectedSettings, offset))

        result
    }

    fun getMore(session: HttpSession): WebServer.ResultList? {
        val (query, selectedSettings, offset) = session.getAttribute("lastQuery") as? LastQuery ?: return null
        val searchSettings = checkUserCanAccessSettings(selectedSettings)
        val requestData = searchSettings.servers
                .filter { it in offset && offset.getValue(it).isNotEmpty() }
                .map { IndexServerRequestData(it, offset[it]) }
        logger.info("Executing query $offset with requestData $requestData")
        val (result, newOffset) = flatten(query.query, dispatcher.dispatchQuery(query, requestData))
        session.setAttribute("lastQuery", LastQuery(query, selectedSettings, newOffset))
        return result
    }

    fun context(query: WebServer.ContextExtensionQuery): SnippetExtension = indexServerConnector.contextExtension(query)

    fun document(query: WebServer.DocumentQuery): WebServer.FullDocument {
        val indexDocument = indexServerConnector.getDocument(query)
        return indexDocument.toWebserverFormat(query.host, query.collection, query.documentId, query.query)
    }


    fun checkUserCanAccessSettings(selectedSettings: Long): SearchSettings {
        val currentUser = userHolder.getCurrentUser()

        val searchSettings = searchSettingsRepository.findById(selectedSettings).orElseThrow { InvalidSearchSettingsException("Unknown searchSettings id $selectedSettings") }

        if (searchSettings.private && (currentUser == null || !currentUser.isAdmin)) {
            throw InvalidSearchSettingsException("Settings is private, current user (or anonymous user) cannot use it")
        }
        return searchSettings
    }

    fun getRawDocument(request: WebServer.RawDocumentRequest): String = indexServerConnector.getRawDocument(request)


    internal fun flatten(query: String, result: Map<String, List<MResult<IndexServer.IndexResultList>>>): Pair<WebServer.ResultList, MutableMap<String, Map<String, Offset>>> {
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
}

