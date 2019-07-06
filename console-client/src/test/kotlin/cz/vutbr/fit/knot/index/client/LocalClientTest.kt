package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientType
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.index.initQueryExecutor
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test


internal lateinit var localCofig: ConsoleClientType.LocalIndex

internal class LocalClientTest {

    companion object {
        @BeforeAll
        @JvmStatic
        internal fun initConfig() {
            val wholeConfig = executeScript<ConsoleClientConfig>("src/test/resources/client.config.local.kts")
            localCofig = wholeConfig.clientType as ConsoleClientType.LocalIndex
        }

    }

    @Test
    fun `execute locally test`() {
        val input = sequenceOf("foo bar baz", "hello", "nertag:person{{nertag->token}}", "lemma:work{{lemma->token}}")
        val executor = initQueryExecutor(localCofig.indexClientConfig)
        for (line in input) {
            executeLocally(executor, line)
        }

    }
}