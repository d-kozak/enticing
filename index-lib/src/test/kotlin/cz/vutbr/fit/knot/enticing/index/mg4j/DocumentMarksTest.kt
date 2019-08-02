package cz.vutbr.fit.knot.enticing.index.mg4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class DocumentMarksTest {

    @Nested
    inner class GluesTest {
        @Test
        fun `non glue elem`() {
            assertThat("asfsad".isGlued()).isFalse()
        }

        @Test
        fun `glue elem`() {
            assertThat("asfsad|G__".isGlued()).isTrue()
        }

        @Test
        fun `remove glue`() {
            assertThat("asfsad|G__".removeGlue()).isEqualTo("asfsad")
            assertThat(",|G__".removeGlue()).isEqualTo(",")
        }
    }
}