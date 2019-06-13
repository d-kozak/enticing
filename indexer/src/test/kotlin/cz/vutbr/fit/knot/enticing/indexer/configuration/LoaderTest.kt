package cz.vutbr.fit.knot.enticing.indexer.configuration

import org.junit.jupiter.api.Test

class LoaderTest {

    @Test
    fun `Load from file`() {
        val config = loadConfiguration("src/test/resources/indexer.config.kts")
        println(config)
    }
}