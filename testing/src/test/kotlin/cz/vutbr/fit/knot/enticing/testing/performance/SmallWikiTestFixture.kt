package cz.vutbr.fit.knot.enticing.testing.performance

import com.github.kittinunf.fuel.httpGet
import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.log.StdoutLogService
import cz.vutbr.fit.knot.enticing.log.measuring

object SmallWikiTestFixture {

    /**
     * id of the search settings to use
     */
    val settingsId = 2

    val configPath = "../deploy/small-wiki/testConfig.kts"

    val config = executeScript<EnticingConfiguration>(configPath).validateOrFail()

    val webserver = config.webserverConfiguration.address

    val queryEndpoint = "http://$webserver:8080/api/v1/query?settings=$settingsId"

    val logger = StdoutLogService(config.loggingConfiguration)
            .measuring(config.loggingConfiguration)


    fun fullInitialization() {
        killTestSetup()
        startTestSetup()
        waitForWebserver("$webserver:8080")
    }

    private fun startTestSetup() = runTestConfig("-wi")
    private fun killTestSetup() = runTestConfig("-wik")

    private fun runTestConfig(args: String) = runManagementCli("$configPath $args")

    private fun waitForWebserver(address: String) {
        for (i in 0 until 10) {
            println("waitting for the webserver...$i")
            try {
                val (_, _, res) = "http://$address".httpGet().responseString()
                println(res.get())
                println("webserver started")
                return
            } catch (ex: Exception) {
                println(ex.message)
                // pass through and try again
            }
            Thread.sleep(3_000)
        }
        throw IllegalStateException("webserver has not started, takes way too long")
    }
}