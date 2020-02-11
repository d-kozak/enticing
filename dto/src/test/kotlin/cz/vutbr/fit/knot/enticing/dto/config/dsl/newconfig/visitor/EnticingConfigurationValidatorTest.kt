package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.enticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.fullConf
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class EnticingConfigurationValidatorTest {

    @Nested
    inner class IndexServerTest {
        @Test
        fun `valid conf`() {
            fullConf.validateOrFail()
        }

        @Test
        fun `should fail without metadata`() {
            val conf = enticingConfiguration {
                corpusConfig {
                    corpus("foo") {
                        indexServers {
                            indexServer {

                            }
                        }
                    }
                }

            }
            assertThrows<IllegalStateException> {
                conf.validateOrFail()
            }
        }
    }


}