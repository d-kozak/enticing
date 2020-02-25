package cz.vutbr.fit.knot.enticing.api

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.log.LogService
import cz.vutbr.fit.knot.enticing.log.logger

abstract class EnticingComponentApi(val address: String, logService: LogService) {

    private val logger = logService.logger { }

    protected fun httpPost(endPoint: String, dto: Any) {
        ("http://$address$endPoint").httpPost()
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