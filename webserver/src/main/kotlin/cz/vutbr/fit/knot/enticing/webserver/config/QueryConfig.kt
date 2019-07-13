package cz.vutbr.fit.knot.enticing.webserver.config

import cz.vutbr.fit.knot.enticing.query.processor.FuelRequestDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueryConfig {

    @Bean
    fun queryDispatcher(@Value("\${index.server.api.base.path}") apiBasePath: String): QueryDispatcher = QueryDispatcher(FuelRequestDispatcher(apiBasePath))

}