package cz.vutbr.fit.knot.enticing.index.server

import org.junit.jupiter.api.Test

class ExecuteMainMethodTest {

    @Test
    fun `execute main method with proper arguments`() {
        // any argument in format --key=value is converted into a property and takes precedence over other property sources(e.g. application.properties)
        main(arrayOf(
                "src/test/resources/client.config.kts",
                """{
  "col1": ["../data/mg4j","../data/indexed"],
  "col2": ["../data/mg4j/cc1.mg4j","../data/indexed"]
}"""
        ))
    }
}