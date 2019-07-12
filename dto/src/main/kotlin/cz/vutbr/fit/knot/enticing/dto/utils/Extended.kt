package cz.vutbr.fit.knot.enticing.dto.utils

import com.fasterxml.jackson.annotation.JsonUnwrapped
import javax.validation.Valid

/**
 * Marker interface denoting class intended to be used as extra info for some other dto
 */
interface Extension

/**
 * Describes a dto with extra information
 *
 * It is basically a more specific Kotlin built-in Pair, with extra annotations
 */
data class Extended<X, Y : Extension>(
        /**
         * Main content of the dto
         */
        @field:JsonUnwrapped
        @field:Valid
        val content: X,
        /**
         * Extra information added to it
         */
        @field:JsonUnwrapped
        @field:Valid
        val extra: Y
)

infix fun <X, Y : Extension> X.with(y: Y) = Extended(this, y)