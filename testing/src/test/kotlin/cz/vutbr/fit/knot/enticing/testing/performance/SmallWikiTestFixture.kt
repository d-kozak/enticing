package cz.vutbr.fit.knot.enticing.testing.performance

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.log.StdoutLogService
import cz.vutbr.fit.knot.enticing.log.measuring

private const val CONFIG_PATH = "../deploy/small-wiki/testConfig.kts"

class SmallWikiTestFixture(rebootComponents: Boolean) {

    val config = executeScript<EnticingConfiguration>(CONFIG_PATH).validateOrFail()

    val logService = StdoutLogService(config.loggingConfiguration)
            .measuring(config.loggingConfiguration)

    val webserverApi: WebserverApi

    init {
        if (rebootComponents) {
            buildLocally()
            killTestSetup()
            startTestSetup()
        }
        webserverApi = webserverLogin(config.webserverConfiguration.fullAddress, "admin", "knot12")
        println(webserverApi.userInfo())
    }

    fun sendQuery(query: String) = webserverApi.sendQuery(query)

    private fun buildLocally() = runTestConfig("--local-build --copy-jars")
    private fun startTestSetup() = runTestConfig("-wim")
    private fun killTestSetup() = runTestConfig("-wimk")

    private fun runTestConfig(args: String) = runManagementCli("$CONFIG_PATH $args")


}