package cz.vutbr.fit.knot.enticing.webserver.testconfig

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.log.StdoutLogService
import cz.vutbr.fit.knot.enticing.log.measuring

val loadedConfiguration = executeScript<EnticingConfiguration>("../dto/src/test/resources/config.kts")
        .validateOrFail()

val dummyLogger = StdoutLogService(loadedConfiguration.loggingConfiguration).measuring()