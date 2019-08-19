package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.annotation.Temporary
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class QueryService(
        private val dispatcher: QueryDispatcher<SearchQuery, Map<CollectionName, Offset>, IndexServer.IndexResultList>,
        private val searchSettingsRepository: SearchSettingsRepository,
        private val userService: EnticingUserService,
        private val indexServerConnector: IndexServerConnector
) {

    private val log = LoggerFactory.getLogger(QueryService::class.java)

    fun query(query: String, selectedSettings: Long): Webserver.ResultList {
        val currentUser = userService.currentUser
        val searchQuery = SearchQuery(query, currentUser?.userSettings?.resultsPerPage
                ?: Defaults.snippetCount)

        val searchSettings = searchSettingsRepository.findById(selectedSettings).orElseThrow { IllegalArgumentException("Unknown searchSettings id $selectedSettings") }

        if (searchSettings.private && (currentUser == null || !currentUser.isAdmin)) {
            throw IllegalArgumentException("Settings is private, current user (or anonymous user) cannot use it")
        }

        val requestData = searchSettings.servers.map { IndexServerRequestData(it) }
        log.info("Executing query $query with requestData $requestData")
        return flatten(dispatcher.dispatchQuery(searchQuery, requestData))
    }


    fun context(query: Webserver.ContextExtensionQuery): SnippetExtension = indexServerConnector.contextExtension(query)

    fun document(query: Webserver.DocumentQuery): Webserver.FullDocument {
        val indexDocument = indexServerConnector.getDocument(query)
        return indexDocument.toWebserverFormat(query.host, query.collection, query.documentId, query.query)
    }


    fun format(selectedSettings: Long): CorpusFormat {
        val currentUser = userService.currentUser
        val searchSettings = searchSettingsRepository.findById(selectedSettings).orElseThrow { IllegalArgumentException("Unknown searchSettings id $selectedSettings") }
        if (searchSettings.private && (currentUser == null || !currentUser.isAdmin)) {
            throw IllegalArgumentException("Settings is private, current user (or anonymous user) cannot use it")
        }

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
}

fun flatten(result: Map<String, List<MResult<IndexServer.IndexResultList>>>): Webserver.ResultList {
    val snippets = mutableListOf<Webserver.SearchResult>()
    val errors = mutableMapOf<ServerId, ErrorMessage>()

    for ((serverId, results) in result) {
        for (serverResult in results) {
            if (serverResult.isSuccess) {
                snippets.addAll(
                        serverResult.value.searchResults.map { it.withHost(serverId) }
                )
            } else {
                val exception = serverResult.exception
                errors[serverId] = "${exception::class.simpleName}:${exception.message}"

                @Temporary("should be delegated to error logging service...when there is one...")
                exception.printStackTrace()
            }
        }
    }

    return Webserver.ResultList(snippets, errors)
}