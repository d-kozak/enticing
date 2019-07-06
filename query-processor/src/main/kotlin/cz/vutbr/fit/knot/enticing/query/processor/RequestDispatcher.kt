package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.query.SearchQuery
import cz.vutbr.fit.knot.enticing.dto.query.ServerInfo
import cz.vutbr.fit.knot.enticing.dto.response.SearchResult
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import org.springframework.http.*
import org.springframework.web.client.RestTemplate


interface RequestDispatcher {
    operator fun invoke(searchQuery: SearchQuery, serverInfo: ServerInfo): MResult<SearchResult>
}

class RestTemplateRequestDispatcher(private val restTemplate: RestTemplate = RestTemplate(), private val path: String = "/api/v1/query") : RequestDispatcher {

    override fun invoke(searchQuery: SearchQuery, serverInfo: ServerInfo): MResult<SearchResult> = MResult.runCatching {
        val input = searchQuery.toJson()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity(input, headers)
        val result = restTemplate.exchange<String>("http://" + serverInfo.address + path, HttpMethod.POST, entity)
        if (result.statusCode == HttpStatus.OK) {
            result.body.toDto<SearchResult>()
        } else {
            // todo somehow rethrow the real exception?
            throw RuntimeException(result.body.toString())
        }
    }
}


inline fun <reified T> RestTemplate.exchange(url: String, method: HttpMethod, requestEntity: HttpEntity<*>) = this.exchange(url, method, requestEntity, T::class.java)