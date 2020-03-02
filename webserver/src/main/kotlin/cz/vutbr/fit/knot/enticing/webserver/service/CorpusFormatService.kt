package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.CorpusFormat
import cz.vutbr.fit.knot.enticing.dto.mergeCorpusFormats
import cz.vutbr.fit.knot.enticing.log.LoggerFactory
import cz.vutbr.fit.knot.enticing.log.logger
import cz.vutbr.fit.knot.enticing.log.measure
import cz.vutbr.fit.knot.enticing.webserver.entity.SearchSettings
import kotlinx.coroutines.*
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CorpusFormatService(
        private val indexServerConnector: IndexServerConnector,
        loggerFactory: LoggerFactory
) {

    private val logger = loggerFactory.logger { }

    @Cacheable("corpusFormat")
    fun loadFormat(searchSettings: SearchSettings): CorpusFormat = logger.measure("loadCorpusFormat", "settingsName=${searchSettings.name}") {
        require(searchSettings.servers.isNotEmpty()) { "Search settings $searchSettings has no associated servers, therefore it has no CorpusFormat" }

        runBlocking(context = Dispatchers.IO) {
            val formats = searchSettings.servers.map { server ->
                async {
                    for (i in 1..10) {
                        try {
                            return@async indexServerConnector.getFormat(server)
                        } catch (ex: Exception) {
                            logger.info("Could not contact server $server: ${ex}, try $i/10")
                            delay(1_000)
                        }
                    }
                    logger.warn("Could not contact server $server")
                    null
                }
            }.awaitAll().filterNotNull()

            mergeCorpusFormats(formats)
        }
    }

}