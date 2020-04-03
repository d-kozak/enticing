package cz.vutbr.fit.knot.enticing.index.client

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Disabled
class IntegrationTests {


    @Test
    fun `index server stdout`() {
        runConsoleClient("-q water -i knot01.fit.vutbr.cz:5627".asArgs())
    }

    @Test
    fun `index server stdout all`() {
        runConsoleClient("-a -c 100 -q water -i knot01.fit.vutbr.cz:5627".asArgs())
    }

    @Test
    fun `index server example query`() {
        runConsoleClient(arrayOf("-q", "a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url", "-i", "knot01.fit.vutbr.cz:5627"))
    }

    @Test
    fun `index server example query id list format`() {
        runConsoleClient(arrayOf("-q", "a:=nertag:person < lemma:(influence | impact | (paid < tribute) ) < b:=nertag:person ctx:sent && a.url != b.url", "--result-format", "idlist", "-i", "knot01.fit.vutbr.cz:5627"))
    }

    @Test
    fun `webserver queries from file`() {
        runConsoleClient("-f src/test/resources/queries.eql -w athena10.fit.vutbr.cz:8080".asArgs())
    }

}