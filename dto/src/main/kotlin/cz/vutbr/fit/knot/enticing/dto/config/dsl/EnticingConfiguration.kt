package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationVisitor

fun enticingConfiguration(block: EnticingConfiguration.() -> Unit) = EnticingConfiguration().apply(block)

/**
 * Global configuration of the whole platform
 */
data class EnticingConfiguration(
        /**
         * configuration of the management service
         */
        var managementServiceConfiguration: ManagementServiceConfiguration = ManagementServiceConfiguration(),
        /**
         * configuration of the webserver
         */
        var webserverConfiguration: WebserverConfiguration = WebserverConfiguration(),
        /**
         * configuration of the logging infrastructure
         */
        var loggingConfiguration: LoggingConfiguration = LoggingConfiguration(),
        /**
         * list of available corpuses
         */
        var corpuses: MutableMap<String, CorpusConfiguration> = mutableMapOf(),

        /**
         * configuration of authentication
         */
        var authentication: EnticingAuthentication = EnticingAuthentication(),

        /**
         * configuration of deployment
         */
        var deploymentConfiguration: DeploymentConfiguration = DeploymentConfiguration(),
        var localHome: String = ""
) : EnticingConfigurationUnit {

    /**
     * errors encountered when initializing the configuration
     */
    internal val errors = mutableListOf<String>()

    fun webserver(block: WebserverConfiguration.() -> Unit) = runCatching {
        webserverConfiguration = WebserverConfiguration().apply(block)
    }


    fun logging(block: LoggingConfiguration.() -> Unit) {
        loggingConfiguration = LoggingConfiguration().apply(block)
    }

    fun management(block: ManagementServiceConfiguration.() -> Unit) = runCatching {
        managementServiceConfiguration = ManagementServiceConfiguration().apply(block)
    }

    fun corpusConfig(block: CorpusMap.() -> Unit) = runCatching {
        corpuses = CorpusMap(::runCatching).apply(block).corpusMap
    }

    fun authentication(block: EnticingAuthentication.() -> Unit) = runCatching {
        authentication = EnticingAuthentication().apply(block)
    }

    fun deployment(block: DeploymentConfiguration.() -> Unit) = runCatching {
        deploymentConfiguration = DeploymentConfiguration().apply(block)
    }

    fun indexServerByAddress(address: String): IndexServerConfiguration {
        for (corpus in corpuses.values) {
            for (server in corpus.indexServers)
                if (server.address == address) return server
        }

        throw IllegalArgumentException("no index server with address $address found")
    }

    private fun runCatching(block: () -> Unit) {
        try {
            block()
        } catch (ex: IllegalStateException) {
            errors.add(ex.message ?: "unknown error")
        }
    }

    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitEnticingConfiguration(this)
    }
}







