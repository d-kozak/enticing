package cz.vutbr.fit.knot.enticing.dto.utils

import com.fasterxml.jackson.annotation.JsonUnwrapped

/**
 * Marker interface denoting class intended to be used as extra info for some other dto
 */
interface ExtraInfo

/**
 * Describes a dto with extra information
 *
 * It is basically a more specific Kotlin build-in pair, with jackson annotations for unwrapping
 */
data class Extended<X, Y : ExtraInfo>(
        /**
         * Main content of the dto
         */
        @field:JsonUnwrapped
        val content: X,
        /**
         * Extra information added to it
         */
        @field:JsonUnwrapped
        val extra: Y
)

infix fun <X, Y : ExtraInfo> X.with(y: Y) = Extended(this, y)