package cz.vutbr.fit.knot.enticing.management

import cz.vutbr.fit.knot.enticing.log.SimpleStdoutLoggerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors


class ManagementEngineTest {

    @Test
    @Disabled
    fun `just config`() {
        val args = parseCliArgs(arrayOf("../dto/src/test/resources/config.kts"))
                .validateOrFail()
        val scope = CoroutineScope(Executors.newFixedThreadPool(4).asCoroutineDispatcher())
        val executor = ManagementEngine(args.configuration, scope, SimpleStdoutLoggerFactory)
        runBlocking(scope.coroutineContext) { executor.execute(args, args.configuration, SimpleStdoutLoggerFactory) }

    }

}