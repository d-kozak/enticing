package cz.vutbr.fit.knot.enticing.index.server.config

import cz.vutbr.fit.knot.enticing.dto.annotation.Incomplete
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexClientConfig
import cz.vutbr.fit.knot.enticing.dto.config.dsl.collection
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.dto.utils.toDto
import cz.vutbr.fit.knot.enticing.index.collection.manager.CollectionManager
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
        @Value("\${collections.config}") private val collectionsConfig: String
) {

    private val log: Logger = LoggerFactory.getLogger(SearchConfig::class.java)

    @Bean
    @Incomplete("add support for tweaking the configuration via commandline arguments")
    fun indexClientConfig(): IndexClientConfig {
        log.info("Loading configuration from $configFile")
        val config = executeScript<IndexClientConfig>(configFile)
        log.info("Loaded config $config")
        log.info("Updating with $collectionsConfig")
        config.update(collectionsConfig)
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
                    .map { initMg4jCollectionManager(config.corpusConfiguration, it) }
                    .map { it.collectionName to it }
                    .toMap()
}

internal fun IndexClientConfig.update(serializedConfig: String) {
    val config = serializedConfig.toDto<Map<String, List<String>>>()
    require(config.isNotEmpty()) { "at least one collection is necessary" }

    collections {
        for ((name, files) in config) {
            collection(name) {
                require(files.size >= 2) { "at least two files are needed" }
                if (files.size == 2) {
                    val (input, output) = files
                    if (input.endsWith(".mg4j")) {
                        mg4jFiles(input)
                        indexDirectory(output)
                    } else {
                        mg4jDirectory(input)
                        indexDirectory(output)
                    }
                } else {
                    mg4jFiles(files.subList(0, files.size - 1))
                    indexDirectory(files.last())
                }
            }
        }
    }
}