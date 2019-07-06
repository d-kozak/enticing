package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.config.SearchConfig
import cz.vutbr.fit.knot.enticing.dto.query.ServerInfo
import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegex

fun consoleClient(block: ConsoleClientConfig.() -> Unit): ConsoleClientConfig = ConsoleClientConfig().apply(block)

class ConsoleClientConfig {

    var searchConfig: SearchConfig = SearchConfig()
    lateinit var clientType: ConsoleClientType

    fun local(block: IndexClientConfig.() -> Unit) = IndexClientConfig().apply(block)
            .let { ConsoleClientType.LocalIndex(it) }
            .also { this.clientType = it }

    fun remote(block: ConsoleClientType.RemoteIndex.() -> Unit) = ConsoleClientType.RemoteIndex().apply(block)
            .also { this.clientType = it }

    fun searchConfig(block: SearchConfig.() -> Unit) = searchConfig.apply(block)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ConsoleClientConfig) return false

        if (searchConfig != other.searchConfig) return false
        if (clientType != other.clientType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = searchConfig.hashCode()
        result = 31 * result + clientType.hashCode()
        return result
    }

    override fun toString(): String {
        return "ConsoleClientConfig(searchConfig=$searchConfig, clientType=$clientType)"
    }
}




sealed class ConsoleClientType {
    data class LocalIndex(val indexClientConfig: IndexClientConfig) : ConsoleClientType()

    data class RemoteIndex(
            var servers: MutableList<ServerInfo> = mutableListOf()
    ) : ConsoleClientType() {

        fun servers(vararg servers: String) {
            servers(servers.toList())
        }

        fun servers(servers: List<String>) {
            val nonMatching = servers.filter { !it.matches(urlRegex) }
            if (nonMatching.isNotEmpty()) {
                throw IllegalArgumentException("$nonMatching are not valid urls")
            }
            this.servers = servers.asSequence().map { ServerInfo(it) }.toMutableList()
        }
    }
}