package cz.vutbr.fit.knot.enticing.index

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.collection.manager.computeAllIntervalCombinations
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


internal class SearchResultIntervalGeneratorTest {

    @Test
    fun `single result`() {
        val input = listOf(listOf(Interval.valueOf(42)))
        Assertions.assertThat(computeAllIntervalCombinations(input))
                .isEqualTo(listOf(Interval.valueOf(42) to listOf(Interval.valueOf(42))))
    }

    @Test
    fun `single results on multiple indexes`() {
        val input = listOf(
                listOf(Interval.valueOf(42)),
                listOf(Interval.valueOf(10, 20)),
                listOf(Interval.valueOf(50, 75))
        )
        Assertions.assertThat(computeAllIntervalCombinations(input))
                .isEqualTo(listOf(Interval.valueOf(10, 75)
                        to listOf(Interval.valueOf(42),
                        Interval.valueOf(10, 20),
                        Interval.valueOf(50, 75))
                ))
    }


    @Test
    fun `two results on one index single results on others`() {
        val input = listOf(
                listOf(Interval.valueOf(42)),
                listOf(Interval.valueOf(10, 20), Interval.valueOf(100, 110)),
                listOf(Interval.valueOf(50, 75))
        )
        Assertions.assertThat(computeAllIntervalCombinations(input))
                .isEqualTo(listOf(Interval.valueOf(10, 75)
                        to listOf(Interval.valueOf(42), Interval.valueOf(10, 20),
                        Interval.valueOf(50, 75)), Interval.valueOf(42, 110)
                        to listOf(Interval.valueOf(42), Interval.valueOf(100, 110), Interval.valueOf(50, 75))
                ))
    }
}