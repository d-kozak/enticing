package cz.vutbr.fit.knot.enticing.index.client

import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.ConsoleClientType
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.index.mg4j.initMg4jCollectionManager
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
            startIndexing(executeScript<IndexBuilderConfig>("../index-builder/src/test/resources/indexer.config.kts").also { it.validate() })

            val wholeConfig = executeScript<ConsoleClientConfig>("src/test/resources/client.config.local.kts").also { it.validate() }
            localConfig = wholeConfig.clientType as ConsoleClientType.LocalIndex
        }

    }

    @Test
    fun `execute locally test`() {
        val input = sequenceOf("foo bar baz", "hello", "nertag:person", "lemma:work")
        val executor = initMg4jCollectionManager(localConfig.indexClientConfig.corpusConfiguration, localConfig.indexClientConfig.collections[0])
        for (line in input) {
            executeLine(executor, line)
        }

    }
}