package cz.vutbr.fit.knot.enticing.testing.performance

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import java.io.File

interface FixtureConfiguration {
    val scriptPath: String
    val searchSettingsPath: String
}

object SmallWiki2018Config : FixtureConfiguration {
    override val scriptPath = "../deploy/small-wiki/testConfig.kts"
    override val searchSettingsPath = "../deploy/small-wiki/small-wiki.search.settings.json"
}

object FullWiki2018Config : FixtureConfiguration {
    override val scriptPath = "../deploy/big-wiki/config.kts"
    override val searchSettingsPath = "../deploy/big-wiki/webserver-search-settings.json"
}


class EnticingTestFixture(val fixtureConfig: FixtureConfiguration, rebootComponents: Boolean) {

    val config = executeScript<EnticingConfiguration>(fixtureConfig.scriptPath).validateOrFail()

    val webserverApi: WebserverApi

    init {
        if (rebootComponents) {
            buildLocally()
            killTestSetup()
            startTestSetup()
        }
        webserverApi = webserverLogin(config.webserverConfiguration.fullAddress, "admin", "knot12")
        if (rebootComponents)
            webserverApi.importSearchSettings(File(fixtureConfig.searchSettingsPath).readText())
        println(webserverApi.userInfo())
    }

    fun sendQuery(query: String) = webserverApi.sendQuery(query)

    fun buildLocally() = runTestConfig("--local-build --copy-jars")
    fun startTestSetup() = runTestConfig("-wim")
    fun killTestSetup() = runTestConfig("-wimk")

    private fun runTestConfig(args: String) = runManagementCli("${fixtureConfig.scriptPath} $args")


}