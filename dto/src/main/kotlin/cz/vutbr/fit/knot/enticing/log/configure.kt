package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.dto.config.dsl.LoggingConfiguration
import java.io.File


fun LoggingConfiguration.configureFor(serviceId: String, managementApi: RemoteLoggingApi? = null): MeasuringLogService {
    val fileLogger = (this.rootDirectory + File.separatorChar + serviceId).asLogger(this)
            .filtered(this.messageTypes)
    val stdoutLogger = StdoutLogService(this)
    val loggers = mutableListOf(stdoutLogger, fileLogger)
    if (managementApi != null) {
        val remoteLogger = RemoteLogService(this, managementApi)
                .filtered(this.managementLoggingConfiguration.messageTypes)
        loggers.add(remoteLogger)
    }
    return DispatchingLogService(loggers)
            .measuring(this)
}

