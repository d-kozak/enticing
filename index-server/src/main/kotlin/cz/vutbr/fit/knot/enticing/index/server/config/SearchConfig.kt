package cz.vutbr.fit.knot.enticing.index.server.config

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexClientConfig
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.index.collection.manager.CollectionManager
import cz.vutbr.fit.knot.enticing.index.collection.manager.initCollectionManager
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
        @Value("\${index.directory}") private val indexDirectory: String,
        @Value("\${mg4j.files}") private val mg4jFiles: List<String>
) {

    private val log: Logger = LoggerFactory.getLogger(SearchConfig::class.java)

    @Bean
    @Incomplete("add support for tweaking the configuration via commandline arguments")
    fun indexClientConfig(): IndexClientConfig {
        log.info("Loading configuration from $configFile")
        val config = executeScript<IndexClientConfig>(configFile)
        log.info("Loaded config $config")
        val errors = config.validate()
        if (errors.isNotEmpty()) {
            throw IllegalArgumentException("$errors")
        }
        return config
    }

    /**
     * Creates SearchExecutors for all collections in the config
     */
    @Bean
    fun collectionManagers(config: IndexClientConfig): Map<String, CollectionManager> =
            config.collections.asSequence()
                    .map { initCollectionManager(config.corpusConfiguration, it) }
                    .map { it.collectionName to it }
                    .toMap()
}