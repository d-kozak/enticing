package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor.EnticingConfigurationVisitor

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
         * list of available corpuses
         */
        var corpuses: MutableList<CorpusConfiguration> = mutableListOf(),
        /**
         * list of separate index servers(not belonging to any corpus)
         */
        var indexServers: MutableList<IndexServerConfiguration> = mutableListOf()) : EnticingConfigurationUnit {

    /**
     * errors encountered when initializing the configuration
     */
    internal val errors = mutableListOf<String>()

    fun webserver(block: WebserverConfiguration.() -> Unit) = runCatching {
        webserverConfiguration = WebserverConfiguration().apply(block)
    }


    fun management(block: ManagementServiceConfiguration.() -> Unit) = runCatching {
        managementServiceConfiguration = ManagementServiceConfiguration().apply(block)
    }

    fun corpusConfig(block: CorpusList.() -> Unit) = runCatching {
        corpuses = CorpusList(::runCatching).apply(block).corpusList
    }

    fun indexServers(block: IndexServerList.() -> Unit) = runCatching {
        indexServers = IndexServerList(null, ::runCatching).apply(block).indexList
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







