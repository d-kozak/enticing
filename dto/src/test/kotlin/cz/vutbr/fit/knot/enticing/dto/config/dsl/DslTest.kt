package cz.vutbr.fit.knot.enticing.dto.config.dsl

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
            params(1)
        }
        assertThat(indexes)
                .isEqualTo(mutableMapOf(
                        "token" to Index("token", "Original token from the document"),
                        "lemma" to Index("lemma", "The lemma of the word", columnIndex = 1),
                        "param0" to Index("param0", columnIndex = 2),
                        "param1" to Index("param1", columnIndex = 3)
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
        val config = indexBuilder {
            inputFiles("1.mg4j", "2.mg4j", "3.mg4j")
            outputDirectory("tmp/output")
            corpus("wiki2018") {
                indexes {
                    index("token") {
                        description = "Original token from the document"
                    }
                    index("lemma") whichIs "The lemma of the word"
                    "tag" whichIs "Tag of the word"
                }
                entities {
                    entity("person") {
                        description = "Person entity"
                        attributes {
                            attribute("name") whichIs "The name of the person"
                        }
                    }
                    entity("artist") {
                        attributes("name", "gender")
                    }
                    "date" with attributes("year", "month", "day")
                }
            }
        }
        val expected = IndexBuilderConfig().apply {
            input = listOf(1, 2, 3).map { "$it.mg4j" }.map { File(it) }
            output = File("tmp/output")
            corpusConfiguration = CorpusConfiguration("wiki2018",
                    indexes = mutableMapOf(
                            "token" to Index("token", "Original token from the document"),
                            "lemma" to Index("lemma", "The lemma of the word", columnIndex = 1),
                            "tag" to Index("tag", "Tag of the word", columnIndex = 2)
                    ),
                    entities = mutableMapOf(
                            "person" to Entity("person", "Person entity", mutableMapOf("name" to Index("name", "The name of the person"))),
                            "artist" to Entity("artist", "", mutableMapOf("name" to Index("name"), "gender" to Index("gender", columnIndex = 1))),
                            "date" to Entity("date", "", mutableMapOf("year" to Index("year"), "month" to Index("month", columnIndex = 1), "day" to Index("day", columnIndex = 2)))
                    )
            )
        }
        assertThat(config)
                .isEqualTo(expected)
    }
}