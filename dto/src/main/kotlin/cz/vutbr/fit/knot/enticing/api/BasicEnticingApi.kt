package cz.vutbr.fit.knot.enticing.api

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.log.logger

class BasicEnticingApi(val address: String, logService: MeasuringLogService) : EnticingApi {

    private val logger = logService.logger { }

    override fun httpPost(endPoint: String, dto: Any) {
        (address + endPoint).httpPost()
                .body(dto.toJson())
                .submit()
    }

    private fun Request.submit() {
        val (_, _, result) = this.responseString()
        if (result is Result.Failure) {
            result.error.exception.message
            logger.error("Failed to submit message")
        }
    }
}