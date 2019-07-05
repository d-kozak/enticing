package cz.vutbr.fit.knot.enticing.index.server.config

import cz.vutbr.fit.knot.enticing.index.QueryExecutor
import cz.vutbr.fit.knot.enticing.index.config.dsl.IndexClientConfig
import cz.vutbr.fit.knot.enticing.index.config.executeScript
import cz.vutbr.fit.knot.enticing.index.initQueryExecutor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class QueryConfig(
        @Value("\${config.file}") private val configFile: String
) {

    private val log: Logger = LoggerFactory.getLogger(QueryConfig::class.java)

    @Bean
    fun queryExecutor(): QueryExecutor {
        // todo allow to change the input indexed files
        log.info("Loading configuration from $configFile")
        val config = executeScript<IndexClientConfig>(configFile)
        log.info("Loaded config $config")
        return initQueryExecutor(config)
    }
}