package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.log.measure
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.flattenResults
import cz.vutbr.fit.knot.enticing.webserver.dto.LastQuery
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import cz.vutbr.fit.knot.enticing.webserver.exception.InvalidSearchSettingsException
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpSession

@Service
class QueryService(
        private val dispatcher: QueryDispatcher<SearchQuery, Map<CollectionName, Offset>, IndexServer.IndexResultList>,
        private val searchSettingsRepository: SearchSettingsRepository,
        private val userHolder: CurrentUserHolder,
        private val indexServerConnector: IndexServerConnector,
        private val compilerService: EqlCompilerService,
        private val corpusFormatService: CorpusFormatService,
        private val resultStorage: TemporaryResultStorage,
        loggerFactory: LoggerFactory
) {

    val logger = loggerFactory.logger { }

    fun validateQuery(query: String, settings: Long) = logger.measure("validateQuery", "query='$query', settingsId=$settings") {
        compilerService.validateQuery(query, format(settings).toMetadataConfiguration())
    }

    fun format(settings: Long) = corpusFormatService.loadFormat(checkUserCanAccessSettings(settings))

    fun query(query: SearchQuery, selectedSettings: Long, session: HttpSession): WebServer.ResultList = logger.measure("query", "query='${query.query}', snippetCount=${query.snippetCount}, settingsId=$selectedSettings") {
        val searchSettings = checkUserCanAccessSettings(selectedSettings)
        compilerService.validateOrFail(query.query, corpusFormatService.loadFormat(searchSettings).toMetadataConfiguration())
        val requestData = searchSettings.servers.map { IndexServerRequestData(it) }
        val result = submitQuery(query, requestData, session, selectedSettings)
        message = "Collected ${result.searchResults.size} snippets"
        result
    }

    fun getMore(session: HttpSession): WebServer.ResultList? {
        val (query, selectedSettings, offset) = session.getAttribute("lastQuery") as? LastQuery ?: return null
        val searchSettings = checkUserCanAccessSettings(selectedSettings)
        val requestData = searchSettings.servers
                .filter { it in offset && offset.getValue(it).isNotEmpty() }
                .map { IndexServerRequestData(it, offset[it]) }
        return submitQuery(query, requestData, session, selectedSettings)
    }


    private fun submitQuery(query: SearchQuery, requestData: List<IndexServerRequestData>, session: HttpSession, selectedSettings: Long): WebServer.ResultList {
        logger.info("Executing query $query with requestData $requestData")
        val onResult = query.uuid?.createCallback()
        val (result, offset) = dispatcher.dispatchQuery(query, requestData, onResult)
                .flattenResults(query.query, logger)

        session.setAttribute("lastQuery", LastQuery(query, selectedSettings, offset))

        if (query.uuid != null)
            resultStorage.markDone(query.uuid.toString())

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

    private fun UUID.createCallback(): ((RequestData<Map<CollectionName, Offset>>, MResult<IndexServer.IndexResultList>) -> Unit)? {
        resultStorage.initEntry(this.toString())
        return { request, result ->
            if (result.isSuccess) {
                val data = result.value.searchResults.map { it.withHost(request.address) }
                resultStorage.addResult(this.toString(), data)
            }
        }
    }

}

