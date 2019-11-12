package cz.vutbr.fit.knot.enticing.query.processor

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpPost
import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.utils.MResult
import cz.vutbr.fit.knot.enticing.query.processor.fuel.awaitDto
import cz.vutbr.fit.knot.enticing.query.processor.fuel.jsonBody

/**
 * Component of QueryDispatcher whose responsibility is to execute the query (which might actually mean sending a request to an index server and therefore delegating the execution further)
 */
interface QueryExecutor<T : Query<T>, OffsetType, Result : QueryResult<OffsetType>> {
    suspend operator fun invoke(searchQuery: T, requestData: RequestData<OffsetType>): MResult<Result>
}

/**
 * Remote query executor that sends request to indexserver via fuel
 *
 * @see https://github.com/kittinunf/fuel
 * It is asynchronous and integrated with coroutines
 */
class FuelQueryExecutor(private val path: String = "/api/v1/query") : QueryExecutor<SearchQuery, Map<CollectionName, Offset>, IndexServer.IndexResultList> {

    override suspend fun invoke(searchQuery: SearchQuery, requestData: RequestData<Map<CollectionName, Offset>>): MResult<IndexServer.IndexResultList> = MResult.runCatching {
        val url = "http://${requestData.address}$path"
        try {
            url.httpPost()
                    .jsonBody(searchQuery.copy(offset = requestData.offset))
                    .awaitDto<IndexServer.IndexResultList>()
        } catch (error: FuelError) {
            throw (QueryDispatcherException(String(error.response.data)))
        }
    }
}