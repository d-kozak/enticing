package cz.vutbr.fit.knot.enticing.dto.interval

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

    operator fun contains(interval: Interval) = from <= interval.from && to >= interval.to

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
        require(lowerBound <= from) { "lower bound should be <= from, $lowerBound,$from" }
        require(upperBound >= to) { "upper bound should be >= to, $upperBound, $to" }
        require(wantedSize >= this.size) { "cannot expand to this size, the interval is already bigger than that, $wantedSize, ${this.size}" }

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

    fun computeOverlap(other: Interval): Double {
        val common = this.commonSubInterval(other)
        if (common.isEmpty()) return 0.0
        return common.size.toDouble() / this.size.toDouble()
    }

    fun commonSubInterval(other: Interval): Interval {
        val (x1, y1) = this
        val (x2, y2) = other
        if (y1 < x2 || y2 < x1) return empty()
        return valueOf(max(x1, x2), min(y1, y2))
    }

    override fun toString(): String = if (size == 1) "[$from]" else "[$from..$to]"


    @JsonIgnore
    val size: Int = if (isEmpty()) 0 else to - from + 1
}

fun findEnclosingInterval(intervals: List<List<Interval>>): Interval {
    require(intervals.isNotEmpty()) { "intervals should not be empty" }
    var min = Int.MAX_VALUE
    var max = Int.MIN_VALUE
    for (x in intervals) {
        require(x.isNotEmpty()) { "intervals should not be empty" }
        for (y in x) {
            if (min > y.from) min = y.from
            if (max < y.to) max = y.to
        }
    }
    return Interval.valueOf(min, max)
}

fun String.substring(interval: Interval) = this.substring(interval.from, interval.to + 1)
