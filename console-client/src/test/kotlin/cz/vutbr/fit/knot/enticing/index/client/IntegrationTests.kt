package cz.vutbr.fit.knot.enticing.index.client

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Disabled
class IntegrationTests {

    @Nested
    inner class SmallWiki {

        val script = "../deploy/small-wiki/testConfig.kts"

        fun runWithArguments(args: String) {
            val split = args.split(" ")

            val arr = Array(1 + split.size) { i ->
                if (i == 0) script else split[i - 1]
            }
            runConsoleClient(arr)
        }

        @Test
        fun `with webserver`() {
            runWithArguments("-w --id 2 -q hello")
        }

        @Test
        fun `queries from file`() {
            runWithArguments("-f src/test/resources/queries.eql")
        }
    }


}