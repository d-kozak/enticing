package cz.vutbr.fit.knot.enticing.index.server.config

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.IndexServerConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.index.collection.manager.CollectionManager
import cz.vutbr.fit.knot.enticing.index.mg4j.CollectionManagerConfiguration
import cz.vutbr.fit.knot.enticing.index.mg4j.initMg4jCollectionManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * Beans related to the searching functionality
 */
@Configuration
class SearchConfig(
        @Value("\${config.file}") private val configFile: String,
        @Value("\${server.address}") private val address: String
) {

    private val log: Logger = LoggerFactory.getLogger(SearchConfig::class.java)

    @Bean
    fun enticingConfiguration(): EnticingConfiguration {
        log.info("Loading configuration from $configFile")
        val config = executeScript<EnticingConfiguration>(configFile)
        log.info("Loaded config $config")
        config.validateOrFail()
        return config
    }

    @Bean
    fun indexServerConfiguration(config: EnticingConfiguration): IndexServerConfiguration = config.indexServerByAddress(address)

    @Bean
    fun metadataConfiguration(config: IndexServerConfiguration): MetadataConfiguration = config.metadataConfiguration
            ?: config.corpus.metadataConfiguration

    @Bean
    fun compiler() = EqlCompiler()

//    @Bean todo fix
//    fun logger(config: IndexClientConfig): LogService = SimpleDirectoryBasedLogService(InetAddress.getLocalHost().hostName + "_index", config.logDirectory)

    /**
     * Creates CollectionManagers for all collections in the config
     */
    @Bean
    fun collectionManagers(config: IndexServerConfiguration, metadataConfiguration: MetadataConfiguration): Map<String, CollectionManager> {
        return config.loadCollections()
                .asSequence()
                .map { (collectionDir, mg4jDir, indexDir) -> CollectionManagerConfiguration(config.corpus.name, collectionDir.name, indexDir, mg4jDir, metadataConfiguration) }
                .map { initMg4jCollectionManager(it) }
                .associateBy { it.collectionName }
    }
}