package cz.vutbr.fit.knot.enticing.webserver.service


import cz.vutbr.fit.knot.enticing.dto.CorpusFormat
import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.SnippetExtension
import cz.vutbr.fit.knot.enticing.dto.Webserver
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.query.processor.exchange
import org.springframework.http.*
import org.springframework.web.client.RestTemplate

class IndexServerConnector(private val template: RestTemplate = RestTemplate(), private val apiBathPath: String) {


    fun getDocument(query: Webserver.DocumentQuery): IndexServer.FullDocument = resultOrThrow(query.host, query.toIndexFormat(), "document")

    fun contextExtension(query: Webserver.ContextExtensionQuery): SnippetExtension = resultOrThrow(query.host, query.toIndexFormat(), "context")

    fun getFormat(server: String) = template.getForEntity("http://$server$apiBathPath/format", CorpusFormat::class.java).body
            ?: throw IllegalStateException("Could no get format from server $server")

    private inline fun <reified T> resultOrThrow(host: String, content: Any, endpoint: String): T {
        val input = content.toJson()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity(input, headers)
        val response = template.exchange<T>("http://$host$apiBathPath/$endpoint", HttpMethod.POST, entity)
        return if (response.statusCode == HttpStatus.OK) {
            response.body!!
        } else {
            @Incomplete("somehow rethrow the real exception?")
            throw RuntimeException(response.body?.toString() ?: "message lost")
        }
    }
}