package cz.vutbr.fit.knot.enticing.dto

import javax.validation.constraints.PositiveOrZero
import kotlin.math.max
import kotlin.math.min

/**
 * Class representing a closed range. Meant to be used instead of mg4j's Interval to decouple the high level classes from mg4j
 */
class Interval private constructor(
        @field:PositiveOrZero
        val from: Int,
        @field:PositiveOrZero
        val to: Int) : Iterable<Int> {

    private constructor(value: Int) : this(value, value)

    companion object {
        fun valueOf(from: Int, to: Int) = Interval(from, to)
        fun valueOf(value: Int) = Interval(value, value)
        fun empty() = Interval(0, -1)
    }

    fun clamp(left: Int = from, right: Int = to) = valueOf(max(left, from), min(right, to))

    fun isEmpty() = from > to

    operator fun contains(value: Int) = value in from..to

    override fun iterator(): Iterator<Int> = object : Iterator<Int> {

        private var current = from

        override fun hasNext(): Boolean = current <= to

        override fun next(): Int = current++
    }
}