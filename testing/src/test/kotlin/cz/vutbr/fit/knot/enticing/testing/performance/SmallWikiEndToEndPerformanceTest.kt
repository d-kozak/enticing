package cz.vutbr.fit.knot.enticing.testing.performance

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test


@Disabled
class SmallWikiEndToEndPerformanceTest : AbstractEnticingPlatformTest(EnticingTestFixture(SmallWiki2018Config, false)) {

    @Test
    fun killAll() {
        fixture.killTestSetup()
    }

}
