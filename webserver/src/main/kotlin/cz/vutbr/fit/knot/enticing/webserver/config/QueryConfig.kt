package cz.vutbr.fit.knot.enticing.webserver.config

import cz.vutbr.fit.knot.enticing.dto.CollectionName
import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.Offset
import cz.vutbr.fit.knot.enticing.dto.SearchQuery
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService
import cz.vutbr.fit.knot.enticing.query.processor.FuelQueryExecutor
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.webserver.service.IndexServerConnector
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class QueryConfig(val logService: MeasuringLogService) {

    @Bean
    fun queryDispatcher(@Value("\${index.server.api.base.path}") apiBasePath: String): QueryDispatcher<SearchQuery, Map<CollectionName, Offset>, IndexServer.IndexResultList> = QueryDispatcher(FuelQueryExecutor("$apiBasePath/query"), logService)

    @Bean
    fun indexServerConnector(@Value("\${index.server.api.base.path}") apiBasePath: String, logService: MeasuringLogService) = IndexServerConnector(RestTemplate(), apiBasePath, logService)
}