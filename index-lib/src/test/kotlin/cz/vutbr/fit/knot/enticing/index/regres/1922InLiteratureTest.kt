package cz.vutbr.fit.knot.enticing.index.regres

import org.junit.jupiter.api.Test

class YearInLiteratureTest : AbstractDocumentMatchingRegressionTest("1922inLiterature") {
    @Test
    fun `1922 in literature`() {
        val query = "nertag:artist"
        val matchInfo = submitQuery(query)
        println(matchInfo)
    }

    @Test
    fun `1922 in literature with global contraints`() {
        val query = "a:=nertag:person b:=nertag:person ctx:sent && a.url != b.url"
        val matchInfo = submitQuery(query)
        println(matchInfo)
    }

}