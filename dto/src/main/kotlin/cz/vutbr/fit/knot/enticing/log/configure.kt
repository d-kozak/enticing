package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.api.ManagementServiceApi
import cz.vutbr.fit.knot.enticing.dto.config.dsl.LoggingConfiguration
import java.io.File


fun LoggingConfiguration.configureFor(serviceId: String, managementAddress: String? = null): MeasuringLogService {
    val fileLogger = (this.rootDirectory + File.separatorChar + serviceId).asLogger(this)
            .filtered(this.messageTypes)
    val stdoutLogger = StdoutLogService(this)
    val dispatchingLogger = DispatchingLogService(stdoutLogger, fileLogger)
    val maybeRemoteLogger: LogService = if (managementAddress != null) {
        val managementApi = ManagementServiceApi(managementAddress, dispatchingLogger)
        val remoteLogger = RemoteLogService(this, managementApi)
                .filtered(this.managementLoggingConfiguration.messageTypes)
        DispatchingLogService(dispatchingLogger, remoteLogger)
    } else dispatchingLogger

    return maybeRemoteLogger.measuring(this)
}

