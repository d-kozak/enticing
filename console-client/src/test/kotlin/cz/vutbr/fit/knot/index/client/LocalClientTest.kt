package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientType
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.index.initQueryExecutor
import cz.vutbr.fit.knot.enticing.index.startIndexing
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal lateinit var localConfig: ConsoleClientType.LocalIndex

internal class LocalClientTest {

    companion object {
        @BeforeAll
        @JvmStatic
        internal fun initConfig() {
            // @cleanup it it necessary to have the indexed data in place for the console client to work, but there is no gradle
            // dependency that would require index-builder tests to execute before console-client, there should be a better way, but currently
            // it is solved by executing the indexing from here
            startIndexing(executeScript("../index-builder/src/test/resources/indexer.config.kts"))

            val wholeConfig = executeScript<ConsoleClientConfig>("src/test/resources/client.config.local.kts")
            localConfig = wholeConfig.clientType as ConsoleClientType.LocalIndex
        }

    }

    @Test
    fun `execute locally test`() {
        val input = sequenceOf("foo bar baz", "hello", "nertag:person{{nertag->token}}", "lemma:work{{lemma->token}}")
        val executor = initQueryExecutor(localConfig.indexClientConfig)
        for (line in input) {
            executeLocally(executor, line)
        }

    }
}