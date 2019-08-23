package cz.vutbr.fit.knot.enticing.index

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
import cz.vutbr.fit.knot.enticing.index.collection.manager.computeAllIntervalCombinations
import cz.vutbr.fit.knot.enticing.index.collection.manager.generateSnippetIntervals
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class SnippetIntervalGeneratorTest {


    @Nested
    inner class GenerateSnippetIntervals {


        @Test
        fun nothingToFilter() {
            val input = listOf(listOf(Interval.valueOf(42)))
            assertThat(generateSnippetIntervals(input, 100))
                    .isEqualTo(listOf(Interval.valueOf(17, 66)))
        }

        @Test
        fun twoIntervals() {
            val input = listOf(
                    listOf(Interval.valueOf(10, 20), Interval.valueOf(50, 60)),
                    listOf(Interval.valueOf(0, 5), Interval.valueOf(70, 90))
            )
            assertThat(generateSnippetIntervals(input, 100))
                    .isEqualTo(listOf(Interval.valueOf(0, 49), Interval.valueOf(45, 94)))
        }

        @Test
        fun threeIntervals() {
            val input = listOf(
                    listOf(Interval.valueOf(10, 20), Interval.valueOf(50, 60)),
                    listOf(Interval.valueOf(0, 5), Interval.valueOf(70, 90)),
                    listOf(Interval.valueOf(20, 30), Interval.valueOf(90, 110))
            )
            assertThat(generateSnippetIntervals(input, 100))
                    .isEqualTo(listOf(Interval.valueOf(0, 49), Interval.valueOf(50, 110)))
        }
    }


    @Nested
    inner class AllIntervalCombinations {
        @Test
        fun `single result`() {
            val input = listOf(listOf(Interval.valueOf(42)))
            assertThat(computeAllIntervalCombinations(input))
                    .isEqualTo(setOf(Interval.valueOf(42)))
        }

        @Test
        fun `single results on multiple indexes`() {
            val input = listOf(
                    listOf(Interval.valueOf(42)),
                    listOf(Interval.valueOf(10, 20)),
                    listOf(Interval.valueOf(50, 75))
            )
            assertThat(computeAllIntervalCombinations(input))
                    .isEqualTo(setOf(Interval.valueOf(10, 75)))
        }


        @Test
        fun `two results on one index single results on others`() {
            val input = listOf(
                    listOf(Interval.valueOf(42)),
                    listOf(Interval.valueOf(10, 20), Interval.valueOf(100, 110)),
                    listOf(Interval.valueOf(50, 75))
            )
            assertThat(computeAllIntervalCombinations(input))
                    .isEqualTo(setOf(Interval.valueOf(10, 75), Interval.valueOf(42, 110)))
        }


        @Test
        fun twoIntervals() {
            val input = listOf(
                    listOf(Interval.valueOf(10, 20), Interval.valueOf(50, 60)),
                    listOf(Interval.valueOf(0, 5), Interval.valueOf(70, 90))
            )
            assertThat(computeAllIntervalCombinations(input))
                    .isEqualTo(setOf(Interval.valueOf(0, 20), Interval.valueOf(0, 60), Interval.valueOf(10, 90), Interval.valueOf(50, 90)))

        }
    }


}