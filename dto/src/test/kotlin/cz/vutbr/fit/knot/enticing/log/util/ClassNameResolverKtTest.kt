package cz.vutbr.fit.knot.enticing.log.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ClassNameResolverKtTest {

    val name = resolveName { }

    @Test
    fun `resolve this test class name`() {
        assertThat(name).isEqualTo("cz.vutbr.fit.knot.enticing.log.util.ClassNameResolverKtTest")
        assertThat(name).isEqualTo("cz.vutbr.fit.knot.enticing.log.util.ClassNameResolverKtTest")
    }
}