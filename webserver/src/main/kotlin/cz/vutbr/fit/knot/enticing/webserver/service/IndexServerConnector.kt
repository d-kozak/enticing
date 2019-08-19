package cz.vutbr.fit.knot.enticing.webserver.service


import cz.vutbr.fit.knot.enticing.dto.CorpusFormat
import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.SnippetExtension
import cz.vutbr.fit.knot.enticing.dto.WebServer
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.query.processor.exchange
import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.web.client.RestTemplate

class IndexServerConnector(private val template: RestTemplate = RestTemplate(), private val apiBathPath: String) {

    private val log = LoggerFactory.getLogger(IndexServerConnector::class.java)

    fun getDocument(query: WebServer.DocumentQuery): IndexServer.FullDocument = resultOrThrow(query.host, query.toIndexFormat(), "document")

    fun contextExtension(query: WebServer.ContextExtensionQuery): SnippetExtension = resultOrThrow(query.host, query.toIndexFormat(), "context")

    fun getFormat(server: String): CorpusFormat {
        val url = "http://$server$apiBathPath/format"
        try {
            val response = template.getForEntity(url, CorpusFormat::class.java)
            return response.body
                    ?: throw IllegalStateException("Could no get format from server $server")
        } catch (ex: Exception) {
            log.warn("Could not get corpus format from $url")
            throw ex
        }
    }

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