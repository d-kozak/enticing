package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random


internal class SegmentTreeTest {

    @Test
    fun `constructor test`() {
        SegmentTree(0, 10000)
    }

    @Test
    fun `constructor test for a single num interval`() {
        SegmentTree(1, 1)
    }


    @Test
    fun `empty interval`() {
        assertThrows<IllegalArgumentException> { SegmentTree(20, 10) }
    }

    @Test
    fun `manual test`() {
        val max = 1000
        val root = SegmentTree(0, max)

        assertThat(root.isFree(0, 0)).isTrue()
        for (left in 0 until max) {
            for (right in left until max) {
                assertThat(root.isFree(left, right))
                        .withFailMessage("Interval [$left,$right] should be empty")
                        .isTrue()
            }
        }
    }


    @Test
    fun `compare with normal array`() {
        val max = 100000
        var root = SegmentTree(0, max - 1)
        var arr = BooleanArray(max) { true }


        var left = 0
        var right = 0
        val newInterval = {
            left = Random.nextInt(max)
            right = Random.nextInt(left, max)
        }

        repeat(1000) {
            println("iter $it")
            newInterval()
            println("Checking interval [$left,$right]")
            val arrAns = (left..right).all { arr[it] }
            val treeAns = root.isFree(left, right)
            val txt = "Answer by tree $treeAns vs array ans $arrAns"
            if (arrAns != treeAns)
                System.err.println(txt)
            assertThat(treeAns)
                    .withFailMessage(txt)
                    .isEqualTo(arrAns)


            var cnt = 0
            do {
                if (cnt == 500) {
                    println("Failed to find an appropriate free interval even after 500 times, resetting the data structures")
                    root = SegmentTree(0, max - 1)
                    arr = BooleanArray(max) { true }
                }
                newInterval()
                cnt++
            } while ((left..right).any { !arr[it] })


            println("Setting interval [$left,$right]")
            for (i in left..right) arr[i] = false
            root.take(left, right)

            if (arr.all { it }) {
                System.err.println("All elements are taken already")
            }
        }
    }
}