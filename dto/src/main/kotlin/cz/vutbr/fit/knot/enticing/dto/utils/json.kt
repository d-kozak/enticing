package cz.vutbr.fit.knot.enticing.dto.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

fun <T> T.toJson(): String = jacksonObjectMapper().writeValueAsString(this)

inline fun <reified T> String.toDto(): T = jacksonObjectMapper().readValue(this, T::class.java)