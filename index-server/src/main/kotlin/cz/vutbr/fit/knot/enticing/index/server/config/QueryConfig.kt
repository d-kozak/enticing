package cz.vutbr.fit.knot.enticing.index.server.config

import cz.vutbr.fit.knot.enticing.index.QueryExecutor
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexClientConfig
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.index.initQueryExecutor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File


@Configuration
class QueryConfig(
        @Value("\${config.file}") private val configFile: String,
        @Value("\${index.directory}") private val indexDirectory: String,
        @Value("\${mg4j.files}") private val mg4jFiles: List<String>
) {

    private val log: Logger = LoggerFactory.getLogger(QueryConfig::class.java)

    @Bean
    fun queryExecutor(): QueryExecutor {
        log.info("Loading configuration from $configFile")
        val config = executeScript<IndexClientConfig>(configFile)
        log.info("Loaded config $config")

        // todo move validation into the configuration

        val indexDirectory = File(indexDirectory)
        indexDirectory.isDirectory || throw IllegalArgumentException("${this.indexDirectory} is not a directory")
        config.indexDirectory = indexDirectory
        log.info("Using index directory ${this.indexDirectory}")

        val inputFiles = mg4jFiles.map { File(it) }
        val nonExistent = inputFiles.filter { !it.exists() }
        nonExistent.isNotEmpty() && throw IllegalArgumentException("Files $nonExistent do not exist")
        config.mg4jFiles = inputFiles
        log.info("Using mg4j files ${this.mg4jFiles}")
        return initQueryExecutor(config)
    }
}