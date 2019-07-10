import cz.vutbr.fit.knot.enticing.dto.config.dsl.consoleClient
import cz.vutbr.fit.knot.enticing.dto.query.ResponseFormat

consoleClient {
    remote {
        //        servers("localhost:8001","localhost:8002","localhost:8003")
        servers("localhost:8001")
    }
    searchConfig {
        responseFormat = ResponseFormat.HTML
    }
}