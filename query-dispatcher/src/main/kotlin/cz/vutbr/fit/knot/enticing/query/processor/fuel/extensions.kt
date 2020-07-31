package cz.vutbr.fit.knot.enticing.query.processor.fuel


import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitString
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson

/**
 * Awaits the request and parses result into given DTO
 */
suspend inline fun <reified T> Request.awaitDto(): T = awaitString().toDto()

/**
 * Includes given DTO class in JSON string as the content of the message
 */
fun <T> Request.jsonBody(any: T) = jsonBody(any.toJson())