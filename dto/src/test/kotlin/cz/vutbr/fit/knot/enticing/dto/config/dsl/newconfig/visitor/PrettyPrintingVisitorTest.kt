package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.loadedConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.prettyPrint
import org.junit.jupiter.api.Test


class PrettyPrintingVisitorTest {

    @Test
    fun `print full config`() {
        loadedConfiguration.prettyPrint()
    }
}