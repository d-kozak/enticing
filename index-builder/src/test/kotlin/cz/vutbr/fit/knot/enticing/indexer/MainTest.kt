package cz.vutbr.fit.knot.enticing.indexer

import org.junit.jupiter.api.Test

class MainTest {

    @Test
    fun `execute main with test config script`() {
        val args = arrayOf("src/test/resources/indexer.config.kts")
        main(args)
    }
}