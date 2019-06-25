package cz.vutbr.fit.knot.index.client

import org.junit.jupiter.api.Test

data class MockInput(val queries: List<String>) {

    var i = 0

    fun readQuery() = if (i < queries.size) queries[i++] else null
}

internal class MainTest {

    @Test
    fun `main loop`() {
        val input = MockInput(listOf(
                "hello",
                "dog",
                "(position:10){{position->token}}"
        ))
        val config = handleArguments(arrayOf("src/test/resources/client.config.kts"))
        val (collection, engine) = initQueryEngine(config)

        responseLoop(collection, config, engine, input::readQuery)
    }
}