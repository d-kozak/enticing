package cz.vutbr.fit.knot.enticing.log

import cz.vutbr.fit.knot.enticing.api.ManagementServiceApi
import cz.vutbr.fit.knot.enticing.dto.config.dsl.LoggingConfiguration
import java.io.File


data class RemoteLoggingConfiguration(val localAddress: String, val managementAddress: String, val componentType: ComponentType)

fun LoggingConfiguration.configureFor(serviceId: String, remoteLoggingConfiguration: RemoteLoggingConfiguration? = null): MeasuringLogService {
    val fileLogger = (this.rootDirectory + File.separatorChar + serviceId).asLogger(this)
            .filtered(this.messageTypes)
    val stdoutLogger = StdoutLogService(this)
    val dispatchingLogger = DispatchingLogService(this, stdoutLogger, fileLogger)
    val maybeRemoteLogger: LogService = if (remoteLoggingConfiguration != null) {
        val (localAddress, managementAddress, componentType) = remoteLoggingConfiguration
        val managementApi = ManagementServiceApi(managementAddress, componentType, localAddress, dispatchingLogger)
        val remoteLogger = RemoteLogService(this, managementApi)
                .filtered(this.managementLoggingConfiguration.messageTypes)
        DispatchingLogService(this, dispatchingLogger, remoteLogger)
    } else dispatchingLogger

    return maybeRemoteLogger.measuring()
}

