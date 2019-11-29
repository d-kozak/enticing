package cz.vutbr.fit.knot.enticing.log

import org.junit.jupiter.api.Test
import java.lang.Thread.sleep

internal class SimpleDirectoryBasedLogServiceTest {


    @Test
    fun simpleLog() {
        val logService = SimpleDirectoryBasedLogService("service1", "foo")
        logService.use {
            logService.reportCrash("bar")
            sleep(1000)
            logService.reportCrash("baz")
        }
    }
}