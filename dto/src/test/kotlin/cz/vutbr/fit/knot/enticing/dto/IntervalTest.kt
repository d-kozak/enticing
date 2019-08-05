package cz.vutbr.fit.knot.enticing.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


internal class IntervalTest {


    @Test
    fun `normal creation`() {
        val interval = Interval.valueOf(10, 20)
        assertThat(interval.from)
                .isEqualTo(10)
        assertThat(interval.to)
                .isEqualTo(20)
    }

    @Test
    fun `single point creation`() {
        val interval = Interval.valueOf(42)
        assertThat(interval.from)
                .isEqualTo(42)
        assertThat(interval.to)
                .isEqualTo(42)
    }

    @Test
    fun `iterator test`() {
        val interval = Interval.valueOf(20, 30)

        for ((i, value) in interval.withIndex()) {
            assertThat(value).isEqualTo(i + 20)
        }
    }

    @Test
    fun `contains test`() {
        val interval = Interval.valueOf(30, 40)
        assertThat(25 in interval).isFalse()
        assertThat(31 in interval).isTrue()
        assertThat(38 in interval).isTrue()
        assertThat(45 in interval).isFalse()
    }

    @Test
    fun `clamp test`() {
        val interval = Interval.valueOf(20, 40)
                .clamp(28, 36)
        assertThat(interval.from)
                .isEqualTo(28)
        assertThat(interval.to)
                .isEqualTo(36)
    }

    @Test
    fun `clamp only upper bound should change`() {
        val interval = Interval.valueOf(20, 40)
                .clamp(15, 35)
        assertThat(interval.from)
                .isEqualTo(20)
        assertThat(interval.to)
                .isEqualTo(35)
    }

    @Test
    fun `is empty test`() {
        val nonEmpty1 = Interval.valueOf(10, 12)
        val nonEmpty2 = Interval.valueOf(21)
        val empty = Interval.empty()
        assertThat(nonEmpty1.isEmpty()).isFalse()
        assertThat(nonEmpty2.isEmpty()).isFalse()
        assertThat(empty.isEmpty()).isTrue()
    }

}