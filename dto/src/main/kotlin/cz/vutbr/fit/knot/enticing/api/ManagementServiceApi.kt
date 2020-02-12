package cz.vutbr.fit.knot.enticing.api

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.log.AggregatingLogService
import cz.vutbr.fit.knot.enticing.log.LogMessage
import cz.vutbr.fit.knot.enticing.log.MeasuringLogService

/**
 * Wrapper around the api of the management service
 */
class ManagementServiceApi(config: EnticingConfiguration, logService: MeasuringLogService)
    : AggregatingLogService(config.loggingConfiguration),
        EnticingApi by BasicEnticingApi(config.managementServiceConfiguration.address!!, logService) {

    override fun onMessage(kind: String, message: String) {
        httpPost("/log", LogMessage(kind, message))
    }
}