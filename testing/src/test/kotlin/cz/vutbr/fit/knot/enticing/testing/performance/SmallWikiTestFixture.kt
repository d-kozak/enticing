package cz.vutbr.fit.knot.enticing.testing.performance

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.log.StdoutLogService
import cz.vutbr.fit.knot.enticing.log.measuring
import java.io.File

private const val CONFIG_PATH = "../deploy/small-wiki/testConfig.kts"
private const val SEARCH_SETTINGS_PATH = "../deploy/small-wiki/small-wiki.search.settings.json"

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
        if (rebootComponents)
            webserverApi.importSearchSettings(File(SEARCH_SETTINGS_PATH).readText())
        println(webserverApi.userInfo())
    }

    fun sendQuery(query: String) = webserverApi.sendQuery(query)

    fun buildLocally() = runTestConfig("--local-build --copy-jars")
    fun startTestSetup() = runTestConfig("-wim")
    fun killTestSetup() = runTestConfig("-wimk")

    private fun runTestConfig(args: String) = runManagementCli("$CONFIG_PATH $args")


}