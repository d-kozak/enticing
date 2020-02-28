package cz.vutbr.fit.knot.enticing.api

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger

abstract class EnticingComponentApi(val componentAddress: String, val componentType: ComponentType, val localAddress: String, loggerFactory: LoggerFactory) {

    private val logger = loggerFactory.logger { }

    protected fun httpPost(endPoint: String, dto: Any) {
        val address = "http://$componentAddress$endPoint"
        logger.info("Http POST $address $dto")
        address.httpPost()
                .jsonBody(dto.toJson())
                .submit()
    }

    private fun Request.submit() {
        val (_, _, result) = this.responseString()
        if (result is Result.Failure) {
            logger.error("Failed to submit message ${result.error.exception.message}")
        }
    }
}