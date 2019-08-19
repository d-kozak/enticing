package cz.vutbr.fit.knot.enticing.index.collection.manager

import it.unimi.dsi.util.Interval

operator fun Interval.component1() = left
operator fun Interval.component2() = right

fun Interval.clamp(fromLeft: Int = left, fromRight: Int = right): Interval = Interval.valueOf(
        if (left < fromLeft) fromLeft else left,
        if (right > fromRight) fromRight else right)

fun Interval.clampRight(limit: Int) = clamp(fromRight = limit)