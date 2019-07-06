package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.query.ServerInfo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
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
            inputFiles("../data/mg4j/cc1.mg4j", "../data/mg4j/cc2.mg4j", "../data/mg4j/cc3.mg4j")
            outputDirectory("../data/indexed")
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
            input = listOf(1, 2, 3).map { "../data/mg4j/cc$it.mg4j" }.map { File(it) }
            output = File("../data/indexed")
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

    @Nested
    inner class ConsoleClientDslTest {

        @Test
        fun `local client`() {
            val config = consoleClient {
                local {
                    mg4jDirectory("../data/mg4j")
                    indexDirectory("../data/indexed")

                    corpus("CC") {
                        indexes {
                            "position" whichIs "Position of the word in the document"
                            "token" whichIs "Original word in the document"
                            "tag" whichIs "tag"
                            "lemma" whichIs "Lemma of the word"
                            "parpos" whichIs "parpos"
                            "function" whichIs "function"
                            "parwrod" whichIs "parword"
                            "parlemma" whichIs "parlemma"
                            "paroffset" whichIs "paroffset"
                            "link" whichIs "link"
                            "length" whichIs "length"
                            "docuri" whichIs "docuri"
                            "lower" whichIs "lower"
                            "nerid" whichIs "nerid"
                            "nertag" whichIs "nertag"
                            params(9)
                            "nertype" whichIs "nertype"
                            "nerlength" whichIs "nerlength"
                        }

                        entities {
                            "person" with attributes("url", "image", "name", "gender", "birthplace", "birthdate", "deathplace", "deathdate", "profession", "nationality")

                            "artist" with attributes("url", "image", "name", "gender", "birthplace", "birthdate", "deathplace", "deathdate", "role", "nationality")

                            "location" with attributes("url", "image", "name", "country")

                            "artwork" with attributes("url", "image", "name", "form", "datebegun", "datecompleted", "movement", "genre", "author")

                            "event" with attributes("url", "image", "name", "startdate", "enddate", "location")

                            "museum" with attributes("url", "image", "name", "type", "established", "director", "location")

                            "family" with attributes("url", "image", "name", "role", "nationality", "members")

                            "group" with attributes("url", "image", "name", "role", "nationality")

                            "nationality" with attributes("url", "image", "name", "country")

                            "date" with attributes("url", "image", "year", "month", "day")

                            "interval" with attributes("url", "image", "fromyear", "frommonth", "fromday", "toyear", "tomonth", "today")

                            "form" with attributes("url", "image", "name")

                            "medium" with attributes("url", "image", "name")

                            "mythology" with attributes("url", "image", "name")

                            "movement" with attributes("url", "image", "name")

                            "genre" with attributes("url", "image", "name")

                        }
                    }
                }
            }

            val expected = ConsoleClientConfig()
            expected.clientType = ConsoleClientType.LocalIndex(indexClient {
                mg4jDirectory("../data/mg4j")
                indexDirectory("../data/indexed")

                corpus("CC") {
                    indexes {
                        "position" whichIs "Position of the word in the document"
                        "token" whichIs "Original word in the document"
                        "tag" whichIs "tag"
                        "lemma" whichIs "Lemma of the word"
                        "parpos" whichIs "parpos"
                        "function" whichIs "function"
                        "parwrod" whichIs "parword"
                        "parlemma" whichIs "parlemma"
                        "paroffset" whichIs "paroffset"
                        "link" whichIs "link"
                        "length" whichIs "length"
                        "docuri" whichIs "docuri"
                        "lower" whichIs "lower"
                        "nerid" whichIs "nerid"
                        "nertag" whichIs "nertag"
                        params(9)
                        "nertype" whichIs "nertype"
                        "nerlength" whichIs "nerlength"
                    }

                    entities {
                        "person" with attributes("url", "image", "name", "gender", "birthplace", "birthdate", "deathplace", "deathdate", "profession", "nationality")

                        "artist" with attributes("url", "image", "name", "gender", "birthplace", "birthdate", "deathplace", "deathdate", "role", "nationality")

                        "location" with attributes("url", "image", "name", "country")

                        "artwork" with attributes("url", "image", "name", "form", "datebegun", "datecompleted", "movement", "genre", "author")

                        "event" with attributes("url", "image", "name", "startdate", "enddate", "location")

                        "museum" with attributes("url", "image", "name", "type", "established", "director", "location")

                        "family" with attributes("url", "image", "name", "role", "nationality", "members")

                        "group" with attributes("url", "image", "name", "role", "nationality")

                        "nationality" with attributes("url", "image", "name", "country")

                        "date" with attributes("url", "image", "year", "month", "day")

                        "interval" with attributes("url", "image", "fromyear", "frommonth", "fromday", "toyear", "tomonth", "today")

                        "form" with attributes("url", "image", "name")

                        "medium" with attributes("url", "image", "name")

                        "mythology" with attributes("url", "image", "name")

                        "movement" with attributes("url", "image", "name")

                        "genre" with attributes("url", "image", "name")

                    }
                }
            })

            assertThat(config)
                    .isEqualTo(expected)
        }
    }

    @Test
    fun `remote client`() {
        val config = consoleClient {
            remote {
                servers("localhost:8001", "localhost:8001", "localhost:8003")
            }
        }

        val servers = listOf("localhost:8001", "localhost:8001", "localhost:8003").map { ServerInfo(it) }.toMutableList()
        val expected = ConsoleClientConfig()
        expected.clientType = ConsoleClientType.RemoteIndex(servers)
        assertThat(config)
                .isEqualTo(expected)
    }
}