package cz.vutbr.fit.knot.enticing.index.server

import org.junit.jupiter.api.Test

class ExecuteMainMethodTest {

    @Test
    fun `execute main method with proper arguments`() {
        // any argument in format --key=value is converted into a property and takes precedence over other property sources(e.g. application.properties)
        main(arrayOf(
                "release",
                "../dto/src/test/resources/config.kts",
                "minerva3.fit.vutbr.cz",
                "--server.port=5627"
        ))
    }
}