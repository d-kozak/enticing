package cz.vutbr.fit.knot.enticing.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.validation.constraints.PositiveOrZero
import kotlin.math.max
import kotlin.math.min

/**
 * Class representing a closed range. Meant to be used instead of mg4j's Interval to decouple the high level classes from mg4j
 */
data class Interval private constructor(
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

    @JsonIgnore
    fun isEmpty() = from > to

    operator fun contains(value: Int) = value in from..to

    override fun iterator(): Iterator<Int> = object : Iterator<Int> {

        private var current = from

        override fun hasNext(): Boolean = current <= to

        override fun next(): Int = current++
    }

    fun combineWith(other: Interval): Interval = when {
        this.isEmpty() -> other
        other.isEmpty() -> this
        else -> valueOf(min(this.from, other.from), max(this.to, other.to))
    }

    fun expand(wantedSize: Int, lowerBound: Int, upperBound: Int): Interval {
        require(lowerBound <= from) { "lower bound should be <= from" }
        require(upperBound >= to) { "upper bound should be <= to" }
        require(wantedSize >= this.size) { "cannot expand to this size, the interval is already bigger than that" }

        val toBeAdded = wantedSize - this.size

        var newFrom = max(from - toBeAdded / 2, lowerBound)
        var newTo = min(to + toBeAdded / 2, upperBound)

        fun currentSize() = newTo - newFrom + 1

        if (currentSize() < wantedSize && newFrom > lowerBound)
            newFrom = max(lowerBound, newFrom - (wantedSize - currentSize()))
        if (currentSize() < wantedSize && newTo < upperBound)
            newTo = min(upperBound, newTo + (wantedSize - currentSize()))

        return valueOf(newFrom, newTo)
    }

    @JsonIgnore
    val size: Int = if (isEmpty()) 0 else to - from + 1
}