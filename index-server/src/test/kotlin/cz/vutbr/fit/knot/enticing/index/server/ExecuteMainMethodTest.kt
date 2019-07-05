package cz.vutbr.fit.knot.enticing.index.server

import org.junit.jupiter.api.Test

class ExecuteMainMethodTest {

    @Test
    fun `execute main method and see what happens`() {
        // any argument in format --key=value is converted into a property and takes precedence over other property sources(e.g. application.properties)
        main(arrayOf("--config.file=src/test/resources/client.config.kts"))
    }
}