package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.fullConf
import org.junit.jupiter.api.Test


class PrettyPrintingVisitorTest {


    @Test
    fun `print full config`() {
        fullConf.prettyPrint()
    }
}