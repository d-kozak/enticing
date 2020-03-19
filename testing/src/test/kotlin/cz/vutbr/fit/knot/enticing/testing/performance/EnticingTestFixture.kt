package cz.vutbr.fit.knot.enticing.testing.performance

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import cz.vutbr.fit.knot.enticing.management.ManagementService
import cz.vutbr.fit.knot.enticing.management.command.concrete.*
import java.io.File

interface FixtureConfiguration {
    val scriptPath: String
    val corpusName: String
    val searchSettingsPath: String
}

object SmallWiki2018Config : FixtureConfiguration {
    override val scriptPath = "../deploy/small-wiki/testConfig.kts"
    override val corpusName: String = "wiki-2018"
    override val searchSettingsPath = "../deploy/small-wiki/small-wiki.search.settings.json"
}

object FullWiki2018Config : FixtureConfiguration {
    override val scriptPath = "../deploy/big-wiki/config.kts"
    override val corpusName: String = "wiki-2018"
    override val searchSettingsPath = "../deploy/big-wiki/webserver-search-settings.json"
}


class EnticingTestFixture(private val fixtureConfig: FixtureConfiguration, rebootComponents: Boolean) : AutoCloseable {

    private val config = executeScript<EnticingConfiguration>(fixtureConfig.scriptPath).validateOrFail()

    private val webserverApi: WebserverApi

    private val managementService = ManagementService(config, SimpleStdoutLoggerFactory)

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

    fun buildLocally() {
        managementService.executeCommand(LocalBuildCommand)
        managementService.executeCommand(CopyJarsCommand)
    }

    fun startTestSetup() {
        managementService.executeCommand(StartManagementServiceCommand)
        managementService.executeCommand(StartIndexServersCommand(fixtureConfig.corpusName))
        managementService.executeCommand(StartWebserverCommand)
    }

    fun killTestSetup() {
        managementService.executeCommand(KillManagementServiceCommand)
        managementService.executeCommand(KillIndexServersCommand(fixtureConfig.corpusName))
        managementService.executeCommand(KillWebserverCommand)
    }

    override fun close() {
        managementService.close()
    }
}