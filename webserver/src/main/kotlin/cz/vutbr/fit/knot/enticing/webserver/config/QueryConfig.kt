package cz.vutbr.fit.knot.enticing.webserver.config

import cz.vutbr.fit.knot.enticing.query.processor.FuelRequestDispatcher
import cz.vutbr.fit.knot.enticing.query.processor.QueryDispatcher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueryConfig {

    @Bean
    fun queryDispatcher(): QueryDispatcher = QueryDispatcher(FuelRequestDispatcher())

}