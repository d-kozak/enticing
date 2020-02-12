package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.LoggingConfiguration
import java.io.File


fun LoggingConfiguration.configureFor(serviceId: String): MeasuringLogService {
    val fileLogger = (this.rootDirectory + File.separatorChar + serviceId).asLogger(this)
            .filtered(this.messageTypes)
    val stdoutLogger = StdoutLogService(this)
    return DispatchingLogService(stdoutLogger, fileLogger)
            .measuring(this)

}

