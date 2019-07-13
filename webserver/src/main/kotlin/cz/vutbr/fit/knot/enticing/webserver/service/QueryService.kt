package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.webserver.repository.SearchSettingsRepository
import cz.vutbr.fit.knot.enticing.webserver.service.mock.dummyDocument
import cz.vutbr.fit.knot.enticing.webserver.service.mock.loremOneSentence
import org.springframework.stereotype.Service


@Service
class QueryService(
        private val dispatcher: QueryDispatcher,
        private val searchSettingsRepository: SearchSettingsRepository,
        private val userService: EnticingUserService
) {

    fun query(query: String, selectedSettings: Long): Webserver.SearchResult {
        val currentUser = userService.currentUser
        val searchQuery = SearchQuery(query, currentUser?.userSettings?.resultsPerPage
                ?: Defaults.snippetCount)

        val searchSettings = searchSettingsRepository.findById(selectedSettings).orElseThrow { IllegalArgumentException("Unknown searchSettings id $selectedSettings") }

        if (searchSettings.private && (currentUser == null || !currentUser.isAdmin)) {
            throw IllegalArgumentException("Settings is private, current user (or anonymous user) cannot use it")
        }

        val serverInfo = searchSettings.servers.map { ServerInfo(it) }
        return flatten(dispatcher.dispatchQuery(searchQuery, serverInfo))
    }


    fun context(query: Webserver.ContextExtensionQuery): Webserver.Snippet = Webserver.Snippet(query.host, query.collection, 42, query.location.toInt(), query.size.toInt() + 10, "http://www.google.com", "title", Payload.FullResponse.Annotated(AnnotatedText(loremOneSentence, emptyMap(), emptyList(), emptyList())), Math.random() > 0.4)

    fun document(query: Webserver.DocumentQuery): Webserver.FullDocument = dummyDocument
}

fun flatten(result: Map<String, List<MResult<IndexServer.SearchResult>>>): Webserver.SearchResult {
    val snippets = mutableListOf<Webserver.Snippet>()
    val errors = mutableMapOf<ServerId, ErrorMessage>()

    for ((serverId, results) in result) {
        for (serverResult in results) {
            if (serverResult.isSuccess) {
                snippets.addAll(
                        serverResult.value.matched.map { it.withHost(serverId) }
                )
            } else {
                val exception = serverResult.exception
                errors[serverId] = "${exception::class.simpleName}:${exception.message}"
            }
        }
    }

    return Webserver.SearchResult(snippets, errors)
}