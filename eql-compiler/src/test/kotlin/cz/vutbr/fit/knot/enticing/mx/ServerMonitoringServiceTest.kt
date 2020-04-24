package cz.vutbr.fit.knot.enticing.mx

import cz.vutbr.fit.knot.enticing.log.ComponentType
import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class ServerMonitoringServiceTest {

    private val service = ServerMonitoringService("foo.bar", ComponentType.INDEX_SERVER, SimpleStdoutLoggerFactory)

    @Test
    fun `get current info`() {
        val status = service.getCurrentServerStatus()
        assertThat(status.freePhysicalMemorySize)
                .isGreaterThan(0.0)
    }
}