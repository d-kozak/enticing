package cz.vutbr.fit.knot.enticing.index.server.config

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CollectionManagerConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexServerConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.eql.compiler.EqlCompiler
import cz.vutbr.fit.knot.enticing.index.collection.manager.CollectionManager
import cz.vutbr.fit.knot.enticing.index.mg4j.initMg4jCollectionManager
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * Beans related to the searching functionality
 */
@Configuration
class SearchConfig(val logService: MeasuringLogService) {

    @Bean
    fun compiler() = EqlCompiler(logService)

    /**
     * Creates CollectionManagers for all collections in the config
     */
    @Bean
    fun collectionManagers(config: IndexServerConfiguration, metadataConfiguration: MetadataConfiguration): Map<String, CollectionManager> {
        return config.loadCollections()
                .asSequence()
                .map { (collectionDir, mg4jDir, indexDir) -> CollectionManagerConfiguration(config.corpus.name, collectionDir.name, mg4jDir, indexDir, metadataConfiguration) }
                .map { initMg4jCollectionManager(it, logService) }
                .associateBy { it.collectionName }
    }
}