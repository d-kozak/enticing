package cz.vutbr.fit.knot.enticing.management

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.executeScript
import cz.vutbr.fit.knot.enticing.log.StdoutLogService
import cz.vutbr.fit.knot.enticing.log.measuring

val config = executeScript<EnticingConfiguration>("../dto/src/test/resources/config.kts")

val stdoutLogService = StdoutLogService(config.loggingConfiguration).measuring(config.loggingConfiguration)