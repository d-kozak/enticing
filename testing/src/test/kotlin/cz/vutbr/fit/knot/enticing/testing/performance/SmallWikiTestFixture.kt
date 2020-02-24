package cz.vutbr.fit.knot.enticing.testing.performance

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.log.StdoutLogService
import cz.vutbr.fit.knot.enticing.log.measuring


class SmallWikiTestFixture(rebootComponents: Boolean) {

    val configPath = "../deploy/small-wiki/testConfig.kts"
    val config = executeScript<EnticingConfiguration>(configPath).validateOrFail()

    val logService = StdoutLogService(config.loggingConfiguration)
            .measuring(config.loggingConfiguration)

    val webserverApi: WebserverApi

    init {
        if (rebootComponents) {
            killTestSetup()
            startTestSetup()
        }
        webserverApi = webserverLogin(config.webserverConfiguration.address + ":8080", "admin", "knot12")
        println(webserverApi.userInfo())
    }

    fun sendQuery(query: String) = webserverApi.sendQuery(query)

    private fun startTestSetup() = runTestConfig("-wi")
    private fun killTestSetup() = runTestConfig("-wik")

    private fun runTestConfig(args: String) = runManagementCli("$configPath $args")


}