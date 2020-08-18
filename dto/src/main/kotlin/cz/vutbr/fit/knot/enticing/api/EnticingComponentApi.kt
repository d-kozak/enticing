package cz.vutbr.fit.knot.enticing.api

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import cz.vutbr.fit.knot.enticing.dto.ComponentAddress
import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.mx.StaticServerInfo

/**
 * Base path of all APIs
 */
@Cleanup("should be loaded from config")
const val API_BASE_PATH = "/api/v1"

/**
 * Base proxy class representing any Enticing component, provides generic methods for communicating with it
 */
open class EnticingComponentApi(loggerFactory: LoggerFactory) {

    protected val logger = loggerFactory.logger { }

    protected inline fun <reified T> httpPost(address: ComponentAddress, endpoint: String, dto: Any): T? {
        val fullAddress = "http://$address$API_BASE_PATH$endpoint"
        logger.debug("Http POST $fullAddress $dto")
        return fullAddress.httpPost()
                .jsonBody(dto.toJson())
                .submit<T>()
    }

    protected inline fun <reified T> Request.submit(): T? {
        val (_, _, result) = this.responseString()
        return if (result is Result.Failure) {
            logger.error("Failed to submit message ${result.error.exception::class} ${result.error.exception.message}")
            result.error.exception.printStackTrace()
            null
        } else {
            if (Unit is T) return Unit else result.component1()?.toDto<T>()
        }
    }

    /**
     * Pings given component
     * @return static info about the server or null if the connection was not successful
     */
    fun ping(address: ComponentAddress): StaticServerInfo? {
        val fullAddress = "http://$address$API_BASE_PATH/server-status"
        return fullAddress.httpGet()
                .submit()
    }
}