package cz.vutbr.fit.knot.enticing.index.regres

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ComicsTest : AbstractDocumentMatchingRegressionTest("76comics") {
    @Test
    fun person() {
        val query = "a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url"
        val matchInfo = submitQuery(query)
        assertThat(matchInfo.intervals).hasSize(2)
        println(matchInfo)
    }

    @Test
    fun artist() {
        val query = "document.uuid:'c9338c23-4580-5ab4-9d86-96de0c0dd15b' a:=nertag:artist < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url"
        val matchInfo = submitQuery(query)
        assertThat(matchInfo.intervals).hasSize(2)
        println(matchInfo)
    }
}