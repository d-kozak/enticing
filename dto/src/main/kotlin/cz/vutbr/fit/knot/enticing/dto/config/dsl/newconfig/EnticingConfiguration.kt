package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

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
        var indexServers: MutableList<IndexServerConfiguration> = mutableListOf()) {

    fun webserver(block: WebserverConfiguration.() -> Unit) {
        webserverConfiguration = WebserverConfiguration().apply(block)
    }

    fun management(block: ManagementServiceConfiguration.() -> Unit) {
        managementServiceConfiguration = ManagementServiceConfiguration().apply(block)
    }

    fun corpusConfig(block: CorpusListDsl.() -> Unit) {
        corpuses = CorpusListDsl().apply(block).corpusList
    }

    fun indexServers(block: IndexServerList.() -> Unit) {
        indexServers = IndexServerList().apply(block).indexList
    }
}







