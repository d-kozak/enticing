package cz.vutbr.fit.knot.enticing.dto.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

val objectMapper = jacksonObjectMapper().findAndRegisterModules()

fun <T> T.toJson(): String = objectMapper.writeValueAsString(this)

inline fun <reified T> String.toDto(): T = objectMapper.readValue(this, T::class.java)

fun String.asJsonObject() = objectMapper.readTree(this)