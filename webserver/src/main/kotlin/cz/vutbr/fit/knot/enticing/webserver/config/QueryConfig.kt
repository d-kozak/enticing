package cz.vutbr.fit.knot.enticing.webserver.config

import cz.vutbr.fit.knot.enticing.dto.ServerInfo
import cz.vutbr.fit.knot.enticing.query.processor.FuelRequestDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import cz.vutbr.fit.knot.enticing.webserver.service.IndexServerConnector
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class QueryConfig {

    @Bean
    fun queryDispatcher(@Value("\${index.server.api.base.path}") apiBasePath: String): QueryDispatcher<ServerInfo> = QueryDispatcher(FuelRequestDispatcher("$apiBasePath/query"))

    @Bean
    fun indexServerConnector(@Value("\${index.server.api.base.path}") apiBasePath: String) = IndexServerConnector(RestTemplate(), apiBasePath)
}