package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.enticingConfiguration
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class EnticingConfigurationValidatorTest {

    @Nested
    inner class IndexServerTest {
        @Test
        fun `valid conf`() {
            val conf = enticingConfiguration {
                indexServers {
                    indexServer {
                        indexedDir = "foo/bar"
                        mg4jDir = "bar/baz"
                        metadata {
                            indexes {
                                index("position")
                                index("word")
                                attributeIndexes(3)
                            }
                            entities {
                                entity("person") {
                                    attributes {
                                        attribute("name")
                                    }
                                }
                                entity("place") {
                                    attributes("name", "location")
                                }

                                extraAttributes("position")

                                entityIndexName = "nertag"
                                lengthIndexName = "nerlength"
                            }
                        }
                    }
                }
            }
            conf.validateOrFail()
        }

        @Test
        fun `should fail without metadata`() {
            val conf = enticingConfiguration {
                indexServers {
                    indexServer {

                    }
                }
            }
            assertThrows<IllegalStateException> {
                conf.validateOrFail()
            }
        }
    }


}