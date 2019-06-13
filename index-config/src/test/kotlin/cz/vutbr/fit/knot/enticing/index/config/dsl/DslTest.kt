package cz.vutbr.fit.knot.enticing.index.config.dsl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

class DslTest {

    @Test
    fun `indexes dsl simple test`() {
        val indexes = indexesDslInternal {
            index("token") {
                description = "Original token from the document"
            }
            index("lemma") whichIs "The lemma of the word"
        }
        assertThat(indexes)
                .isEqualTo(mutableMapOf(
                        "token" to Index("token", "Original token from the document"),
                        "lemma" to Index("lemma", "The lemma of the word", columnIndex = 1)
                ))
    }

    @Test
    fun `entities simple dsl`() {
        val entities = entitiesDslInternal {
            entity("person") {
                description = "Person entity"
                attributes {
                    attribute("name") whichIs "The name of the person"
                }
            }
        }
        assertThat(entities)
                .isEqualTo(mutableMapOf(
                        "person" to Entity("person", "Person entity", mutableMapOf("name" to Index("name", "The name of the person")))
                ))
    }

    @Test
    fun `corpus simple test`() {
        val corpus = corpusDslInternal("wiki2018") {
            indexes {
                index("token") {
                    description = "Original token from the document"
                }
                index("lemma") whichIs "The lemma of the word"
            }
            entities {
                entity("person") {
                    description = "Person entity"
                    attributes {
                        attribute("name") whichIs "The name of the person"
                    }
                }
            }
        }
        assertThat(corpus)
                .isEqualTo(CorpusConfiguration("wiki2018",
                        indexes = mutableMapOf(
                                "token" to Index("token", "Original token from the document"),
                                "lemma" to Index("lemma", "The lemma of the word", columnIndex = 1)
                        ),
                        entities = mutableMapOf(
                                "person" to Entity("person", "Person entity", mutableMapOf("name" to Index("name", "The name of the person")))
                        )
                ))
    }

    @Test
    fun `Indexer config simple test`() {
        val config = indexerConfiguration {
            inputFiles("1.mg4j", "2.mg4j", "3.mg4j")
            outputDirectory("tmp/output")
            corpus("wiki2018") {
                indexes {
                    index("token") {
                        description = "Original token from the document"
                    }
                    index("lemma") whichIs "The lemma of the word"
                }
                entities {
                    entity("person") {
                        description = "Person entity"
                        attributes {
                            attribute("name") whichIs "The name of the person"
                        }
                    }
                }
            }
        }
        val expected = IndexerConfig().apply {
            input = listOf(1, 2, 3).map { "$it.mg4j" }.map { File(it) }
            output = File("tmp/output")
            corpusConfiguration = CorpusConfiguration("wiki2018",
                    indexes = mutableMapOf(
                            "token" to Index("token", "Original token from the document"),
                            "lemma" to Index("lemma", "The lemma of the word", columnIndex = 1)
                    ),
                    entities = mutableMapOf(
                            "person" to Entity("person", "Person entity", mutableMapOf("name" to Index("name", "The name of the person")))
                    )
            )
        }
        assertThat(config)
                .isEqualTo(expected)
    }
}