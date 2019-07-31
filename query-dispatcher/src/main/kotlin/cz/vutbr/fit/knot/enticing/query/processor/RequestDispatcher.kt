package cz.vutbr.fit.knot.enticing.query.processor

import com.github.kittinunf.fuel.httpPost
import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.query.processor.fuel.awaitDto
import cz.vutbr.fit.knot.enticing.query.processor.fuel.jsonBody
import org.springframework.http.*
import org.springframework.web.client.RestTemplate

interface RequestDispatcher<T : Query<T>,OffsetType, Result : QueryResult<OffsetType>> {
    suspend operator fun invoke(searchQuery: T, requestData: RequestData<OffsetType>): MResult<Result>
}

/**
 * Request dispatcher implemented using the Fuel library
 *
 * @see https://github.com/kittinunf/fuel
 * It is asynchronous and integrated with coroutines
 */
class FuelRequestDispatcher(private val path: String = "/api/v1/query") : RequestDispatcher<SearchQuery,Map<CollectionName,Offset>, IndexServer.SearchResult> {

    override suspend fun invoke(searchQuery: SearchQuery, requestData: RequestData<Map<CollectionName, Offset>>): MResult<IndexServer.SearchResult> = MResult.runCatching {
        val url = "http://${requestData.address}$path"
        url.httpPost()
                .jsonBody(searchQuery.copy(offset = requestData.offset))
                .awaitDto<IndexServer.SearchResult>()
    }
}


class RestTemplateRequestDispatcher(private val restTemplate: RestTemplate = RestTemplate(), private val path: String = "/api/v1/query") : RequestDispatcher<SearchQuery,Map<CollectionName,Offset>, IndexServer.SearchResult> {

    override suspend fun invoke(searchQuery: SearchQuery, requestData: RequestData<Map<CollectionName, Offset>>): MResult<IndexServer.SearchResult> = MResult.runCatching {
        val input = searchQuery
                .copy(offset = requestData.offset)
                .toJson()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity(input, headers)
        val result = restTemplate.exchange<String>("http://" + requestData.address + path, HttpMethod.POST, entity)
        if (result.statusCode == HttpStatus.OK) {
            result.body!!.toDto<IndexServer.SearchResult>()
        } else {
            @Incomplete("somehow rethrow the real exception?")
            throw RuntimeException(result.body.toString())
        }
    }
}


inline fun <reified T> RestTemplate.exchange(url: String, method: HttpMethod, requestEntity: HttpEntity<*>) = this.exchange(url, method, requestEntity, T::class.java)