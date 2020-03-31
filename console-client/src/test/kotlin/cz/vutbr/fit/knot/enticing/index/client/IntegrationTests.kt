package cz.vutbr.fit.knot.enticing.index.client

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import runConsoleClient
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Disabled
class IntegrationTests {


    @Test
    fun `index server stdout`() {
        runConsoleClient("-q ahoj -i knot01.fit.vutbr.cz:5627".asArgs())
    }

    @Test
    fun `webserver queries from file`() {
        runConsoleClient("-f src/test/resources/queries.eql -w athena10.fit.vutbr.cz:8080".asArgs())
    }

}