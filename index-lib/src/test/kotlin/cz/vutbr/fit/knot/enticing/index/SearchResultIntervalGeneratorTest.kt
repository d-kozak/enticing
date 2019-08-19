package cz.vutbr.fit.knot.enticing.index

import cz.vutbr.fit.knot.enticing.index.collection.manager.computeAllIntervalCombinations
import it.unimi.di.big.mg4j.query.SelectedInterval
import it.unimi.dsi.util.Interval
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


internal class SearchResultIntervalGeneratorTest {

    @Test
    fun `single result`() {
        val input = listOf(arrayOf(SelectedInterval(Interval.valueOf(42), SelectedInterval.IntervalType.WHOLE)))
        Assertions.assertThat(computeAllIntervalCombinations(input))
                .isEqualTo(listOf(cz.vutbr.fit.knot.enticing.dto.interval.Interval.valueOf(42) to listOf(cz.vutbr.fit.knot.enticing.dto.interval.Interval.valueOf(42))))
    }

    @Test
    fun `single results on multiple indexes`() {
        val input = listOf(
                arrayOf(SelectedInterval(Interval.valueOf(42), SelectedInterval.IntervalType.WHOLE)),
                arrayOf(SelectedInterval(Interval.valueOf(10, 20), SelectedInterval.IntervalType.WHOLE)),
                arrayOf(SelectedInterval(Interval.valueOf(50, 75), SelectedInterval.IntervalType.WHOLE))
        )
        Assertions.assertThat(computeAllIntervalCombinations(input))
                .isEqualTo(listOf(cz.vutbr.fit.knot.enticing.dto.interval.Interval.valueOf(10, 75)
                        to listOf(cz.vutbr.fit.knot.enticing.dto.interval.Interval.valueOf(42),
                        cz.vutbr.fit.knot.enticing.dto.interval.Interval.valueOf(10, 20),
                        cz.vutbr.fit.knot.enticing.dto.interval.Interval.valueOf(50, 75))
                ))
    }


    @Test
    fun `two results on one index single results on others`() {
        val input = listOf(
                arrayOf(SelectedInterval(Interval.valueOf(42), SelectedInterval.IntervalType.WHOLE)),
                arrayOf(SelectedInterval(Interval.valueOf(10, 20), SelectedInterval.IntervalType.WHOLE), SelectedInterval(Interval.valueOf(100, 110), SelectedInterval.IntervalType.WHOLE)),
                arrayOf(SelectedInterval(Interval.valueOf(50, 75), SelectedInterval.IntervalType.WHOLE))
        )
        Assertions.assertThat(computeAllIntervalCombinations(input))
                .isEqualTo(listOf(cz.vutbr.fit.knot.enticing.dto.interval.Interval.valueOf(10, 75)
                        to listOf(cz.vutbr.fit.knot.enticing.dto.interval.Interval.valueOf(42), cz.vutbr.fit.knot.enticing.dto.interval.Interval.valueOf(10, 20),
                        cz.vutbr.fit.knot.enticing.dto.interval.Interval.valueOf(50, 75)), cz.vutbr.fit.knot.enticing.dto.interval.Interval.valueOf(42, 110)
                        to listOf(cz.vutbr.fit.knot.enticing.dto.interval.Interval.valueOf(42), cz.vutbr.fit.knot.enticing.dto.interval.Interval.valueOf(100, 110), cz.vutbr.fit.knot.enticing.dto.interval.Interval.valueOf(50, 75))
                ))
    }
}