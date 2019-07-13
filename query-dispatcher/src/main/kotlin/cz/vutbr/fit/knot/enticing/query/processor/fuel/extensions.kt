package cz.vutbr.fit.knot.enticing.query.processor.fuel


import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitString
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson

suspend inline fun <reified T> Request.awaitDto(): T = awaitString().toDto()

fun <T> Request.jsonBody(any: T) = jsonBody(any.toJson())