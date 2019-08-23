package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
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
        assertThat(one.combineWith(two))
                .isEqualTo(Interval.valueOf(10, 50))
    }

    @Test
    fun `extend with test overlap`() {
        val one = Interval.valueOf(10, 20)
        val two = Interval.valueOf(15, 25)
        assertThat(one.combineWith(two))
                .isEqualTo(Interval.valueOf(10, 25))
    }

    @Test
    fun `extend with this interval is empty`() {
        val one = Interval.empty()
        val two = Interval.valueOf(15, 25)
        assertThat(one.combineWith(two))
                .isEqualTo(Interval.valueOf(15, 25))
    }

    @Test
    fun `extend with second interval is empty`() {
        val one = Interval.valueOf(15, 25)
        val two = Interval.empty()
        assertThat(one.combineWith(two))
                .isEqualTo(Interval.valueOf(15, 25))
    }

    @Test
    fun `empty intervals are equal`() {
        assertThat(Interval.empty())
                .isEqualTo(Interval.empty())
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
    inner class Expand {

        @Test
        fun `even expansion on both sides`() {
            val input = Interval.valueOf(41, 50)
            assertThat(input.expand(20, 0, 100))
                    .isEqualTo(Interval.valueOf(36, 55))
        }

        @Test
        fun `everything goes left no space right`() {
            val input = Interval.valueOf(21, 30)
            assertThat(input.expand(20, 0, 30))
                    .isEqualTo(Interval.valueOf(11, 30))
        }

        @Test
        fun `everything goes right not space left`() {
            val input = Interval.valueOf(31, 50)
            assertThat(input.expand(30, 31, 100))
                    .isEqualTo(Interval.valueOf(31, 60))
        }

        @Test
        fun `cannot expand at all`() {
            val input = Interval.valueOf(10, 20)
            assertThat(input.expand(100, 10, 20))
                    .isEqualTo(Interval.valueOf(10, 20))
        }

        @Test
        fun `no need to expand`() {
            val input = Interval.valueOf(10)
            assertThat(input.expand(1, 0, 100))
                    .isEqualTo(Interval.valueOf(10))
        }

        @Test
        fun `expand one left`() {
            val input = Interval.valueOf(11, 14)
            assertThat(input.expand(5, 0, 14))
                    .isEqualTo(Interval.valueOf(10, 14))
        }

        @Test
        fun `expand one left three right`() {
            val input = Interval.valueOf(11, 14)
            assertThat(input.expand(8, 10, 100))
                    .isEqualTo(Interval.valueOf(10, 17))
        }

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

    @Nested
    inner class CommonSubInterval {

        @Test
        fun `nothing in common`() {
            val x = Interval.valueOf(10, 20)
            val y = Interval.valueOf(30, 40)
            assertThat(x.commonSubInterval(y))
                    .isEqualTo(Interval.empty())
        }

        @Test
        fun `share three to five`() {
            val x = Interval.valueOf(1, 5)
            val y = Interval.valueOf(3, 7)
            assertThat(x.commonSubInterval(y))
                    .isEqualTo(Interval.valueOf(3, 5))
        }

        @Test
        fun `y in subinterval of x`() {
            val x = Interval.valueOf(1, 5)
            val y = Interval.valueOf(2, 3)
            assertThat(x.commonSubInterval(y))
                    .isEqualTo(Interval.valueOf(2, 3))
        }

        @Test
        fun `x in subinterval of y`() {
            val x = Interval.valueOf(2, 3)
            val y = Interval.valueOf(1, 5)
            assertThat(x.commonSubInterval(y))
                    .isEqualTo(Interval.valueOf(2, 3))
        }

        @Test
        fun `same intervals`() {
            val x = Interval.valueOf(2, 3)
            val y = Interval.valueOf(2, 3)
            assertThat(x.commonSubInterval(y))
                    .isEqualTo(Interval.valueOf(2, 3))
        }
    }


    @Nested
    inner class Overlapping {

        @Test
        fun `interval have nothing in common`() {
            val x = Interval.valueOf(10, 20)
            val y = Interval.valueOf(30, 40)
            assertThat(x.computeOverlap(y))
                    .isEqualTo(0.0)

        }

        @Test
        fun `share three to five`() {
            val x = Interval.valueOf(1, 5)
            val y = Interval.valueOf(3, 7)
            assertThat(x.computeOverlap(y))
                    .isEqualTo(3.0 / 5.0)
        }

        @Test
        fun `y in subinterval of x`() {
            val x = Interval.valueOf(1, 5)
            val y = Interval.valueOf(2, 3)
            assertThat(x.computeOverlap(y))
                    .isEqualTo(2.0 / 5.0)
        }

        @Test
        fun `x in subinterval of y`() {
            val x = Interval.valueOf(2, 3)
            val y = Interval.valueOf(1, 5)
            assertThat(x.computeOverlap(y))
                    .isEqualTo(1.0)
        }

        @Test
        fun `same intervals`() {
            val x = Interval.valueOf(2, 3)
            val y = Interval.valueOf(2, 3)
            assertThat(x.computeOverlap(y))
                    .isEqualTo(1.0)
        }
    }
}