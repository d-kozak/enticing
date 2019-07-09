package cz.vutbr.fit.knot.enticing.index.server.config

import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexClientConfig
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.index.query.QueryExecutor
import cz.vutbr.fit.knot.enticing.index.query.initQueryExecutor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class QueryConfig(
        @Value("\${config.file}") private val configFile: String,
        @Value("\${index.directory}") private val indexDirectory: String,
        @Value("\${mg4j.files}") private val mg4jFiles: List<String>
) {

    private val log: Logger = LoggerFactory.getLogger(QueryConfig::class.java)

    @Bean
    fun queryExecutor(): QueryExecutor {
        // todo each index server should support multiple collections, not just one

        log.info("Loading configuration from $configFile")
        val config = executeScript<IndexClientConfig>(configFile)
        log.info("Loaded config $config")
        config.indexDirectory(indexDirectory)
        log.info("Using index directory ${this.indexDirectory}")
        config.mg4jFiles(mg4jFiles)
        log.info("Using mg4j files ${this.mg4jFiles}")

        val errors = config.validate()
        if (errors.isNotEmpty()) {
            throw IllegalArgumentException("$errors")
        }

        return initQueryExecutor(config)
    }
}