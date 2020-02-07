package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

fun enticingConfiguration(block: EnticingConfiguration.() -> Unit) = EnticingConfiguration().apply(block)

/**
 * Global configuration of the whole platform
 */
class EnticingConfiguration {

    var managementServiceConfiguration = ManagementServiceConfiguration()

    var webserverConfiguration = WebserverConfiguration()

    /**
     * list of available corpuses
     */
    var corpuses = mutableListOf<CorpusConfiguration>()

    /**
     * list of separate index servers(not belonging to any corpus)
     */
    var indexServers = mutableListOf<IndexServerConfiguration>()

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







