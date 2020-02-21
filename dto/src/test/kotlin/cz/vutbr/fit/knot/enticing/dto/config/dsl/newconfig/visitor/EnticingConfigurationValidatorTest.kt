package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.enticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.loadedConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.validateOrFail
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class EnticingConfigurationValidatorTest {

    @Nested
    inner class IndexServerTest {
        @Test
        fun `valid conf`() {
            loadedConfiguration.validateOrFail()
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