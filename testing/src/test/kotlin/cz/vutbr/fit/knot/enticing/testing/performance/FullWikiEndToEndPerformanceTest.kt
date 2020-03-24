package cz.vutbr.fit.knot.enticing.testing.performance

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class FullWikiEndToEndPerformanceTest : AbstractEnticingPlatformTest(EnticingTestFixture(FullWiki2018Config, false)) {

    @Test
    fun killAll() {
        fixture.killTestSetup()
    }


}