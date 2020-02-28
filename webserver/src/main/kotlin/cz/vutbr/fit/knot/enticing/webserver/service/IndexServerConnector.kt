package cz.vutbr.fit.knot.enticing.webserver.service


import cz.vutbr.fit.knot.enticing.api.ComponentNotAccessibleException
import cz.vutbr.fit.knot.enticing.dto.CorpusFormat
import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.SnippetExtension
import cz.vutbr.fit.knot.enticing.dto.WebServer
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.annotation.WhatIf
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import org.springframework.http.*
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate

class IndexServerConnector(private val template: RestTemplate = RestTemplate(), private val apiBathPath: String, loggerFactory: LoggerFactory) {

    val logger = loggerFactory.logger { }

    fun getDocument(query: WebServer.DocumentQuery): IndexServer.FullDocument = resultOrThrow(query.host, query.toIndexFormat(), "document")

    fun contextExtension(query: WebServer.ContextExtensionQuery): SnippetExtension = resultOrThrow(query.host, query.toIndexFormat(), "context")

    fun getFormat(server: String): CorpusFormat {
        val url = "http://$server$apiBathPath/format"
        try {
            val response = template.getForEntity(url, CorpusFormat::class.java)
            return response.body
                    ?: throw IllegalStateException("Could no get format from server $server")
        } catch (ex: ResourceAccessException) {
            throw ComponentNotAccessibleException("Could not get corpus format from $url", ex)
        }
    }

    private inline fun <reified T> resultOrThrow(host: String, content: Any, endpoint: String): T {
        val input = content.toJson()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity(input, headers)
        val url = "http://$host$apiBathPath/$endpoint"
        val response = template.exchange<T>(url, HttpMethod.POST, entity)
        return if (response.statusCode == HttpStatus.OK) {
            response.body!!
        } else {
            @Incomplete("somehow rethrow the real exception?")
            throw ComponentNotAccessibleException("Could not get corpus format from $url: ${response.body}")
        }
    }

    fun getRawDocument(request: WebServer.RawDocumentRequest): String {
        var url = "http://${request.server}$apiBathPath/raw-document/${request.collection}/${request.documentId}?"
        if (request.from != null) url += "&from=${request.from}"
        if (request.to != null) url += "&to=${request.to}"
        try {
            val response = template.getForEntity(url, String::class.java)
            return response.body
                    ?: throw IllegalStateException("Request $request failed")
        } catch (ex: ResourceAccessException) {
            throw ComponentNotAccessibleException("Could not get corpus format from $url")
        }
    }

    @WhatIf("create special endpoint or maybe use spring actuator?")
    fun getStatus(server: String): String {
        val url = "http://$server$apiBathPath/format"
        return try {
            val response = template.getForEntity(url, CorpusFormat::class.java)
            if (response.body != null) "RUNNING" else throw IllegalStateException("Could no get format from server $server")
        } catch (ex: ResourceAccessException) {
            logger.warn("Server $server is not responding correctly")
            ex.message!!
        }
    }
}

inline fun <reified T> RestTemplate.exchange(url: String, method: HttpMethod, requestEntity: HttpEntity<*>) = this.exchange(url, method, requestEntity, T::class.java)