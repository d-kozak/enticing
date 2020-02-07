package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

import org.junit.jupiter.api.Test


class EnticingConfigurationTest {

    @Test
    fun `simple config`() {
        val conf = enticingConfiguration {
            webserver {

            }

            management {
                hearthBeat = true
            }

            corpusConfig {
                corpus {

                }
            }
        }
    }
}