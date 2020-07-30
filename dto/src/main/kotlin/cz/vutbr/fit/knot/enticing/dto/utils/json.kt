package cz.vutbr.fit.knot.enticing.dto.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

val objectMapper: ObjectMapper = jacksonObjectMapper().findAndRegisterModules()

/**
 * Serializes given DTO into JSON string
 */
fun <T> T.toJson(): String = objectMapper.writeValueAsString(this)

/**
 * Parses DTO from a JSON string
 */
inline fun <reified T> String.toDto(): T = objectMapper.readValue(this, T::class.java)

/**
 * Parses a string into a JsonNode object
 */
fun String.asJsonObject() = objectMapper.readTree(this)