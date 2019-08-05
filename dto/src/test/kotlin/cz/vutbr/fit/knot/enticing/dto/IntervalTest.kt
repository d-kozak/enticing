package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.dto.utils.toJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
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

    @Test
    fun `destructuring test`() {
        val (from, to) = Interval.valueOf(11, 13)

        assertThat(from).isEqualTo(11)
        assertThat(to).isEqualTo(13)
    }

    @Test
    fun `extend with test`() {
        val one = Interval.valueOf(10, 20)
        val two = Interval.valueOf(40, 50)
        assertThat(one.extendWith(two))
                .isEqualTo(Interval.valueOf(10, 50))
    }

    @Test
    fun `extend with test overlap`() {
        val one = Interval.valueOf(10, 20)
        val two = Interval.valueOf(15, 25)
        assertThat(one.extendWith(two))
                .isEqualTo(Interval.valueOf(10, 25))
    }

    @Test
    fun `extend with this interval is empty`() {
        val one = Interval.empty()
        val two = Interval.valueOf(15, 25)
        assertThat(one.extendWith(two))
                .isEqualTo(Interval.valueOf(15, 25))
    }

    @Test
    fun `extend with second interval is empty`() {
        val one = Interval.valueOf(15, 25)
        val two = Interval.empty()
        assertThat(one.extendWith(two))
                .isEqualTo(Interval.valueOf(15, 25))
    }

    @Test
    fun `size test`() {
        val normal = Interval.valueOf(10, 20)
        assertThat(normal.size)
                .isEqualTo(11)

        val oneElem = Interval.valueOf(42)
        assertThat(oneElem.size)
                .isEqualTo(1)

        val empty = Interval.empty()
        assertThat(empty.size)
                .isEqualTo(0)
    }


    @Nested
    inner class Serialization {


        @Test
        fun `serialization test`() {
            val input = Interval.valueOf(20, 40)
            assertThat(input.toJson())
                    .contains("from", "to")
                    .doesNotContain("size", "empty")
        }

        @Test
        fun `serialization and deserialization`() {
            val input = Interval.valueOf(100, 200)
            assertThat(input.toJson().toDto<Interval>())
                    .isEqualTo(Interval.valueOf(100, 200))
        }

    }
}