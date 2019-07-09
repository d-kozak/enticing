package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.config.SearchConfig
import cz.vutbr.fit.knot.enticing.dto.query.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File

class DslTest {

    @Nested
    inner class CorpusConfig {
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
            val corpus = corpusConfig("wiki2018") {
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
                entityMapping {
                    entityIndex = "nertag"
                    attributeIndexes = 15 to 24
                    extraIndexes("nertype", "nerlength")
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
                            ),
                            entityMapping = EntityMapping().apply {
                                entityIndex = "nertag"
                                attributeIndexes = 15 to 24
                                extraEntityIndexes = setOf("nertype", "nerlength")
                            }
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
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = 15 to 24
                        extraIndexes("nertype", "nerlength")
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
                        ),
                        entityMapping = EntityMapping().apply {
                            entityIndex = "nertag"
                            attributeIndexes = 15 to 24
                            extraEntityIndexes = setOf("nertype", "nerlength")
                        }
                )
            }
            assertThat(config)
                    .isEqualTo(expected)
        }


        @Nested
        inner class EntityFilteringTest {

            @Test
            fun `toplevel predefined none`() {
                val requirement = TextMetadata.Predefined("none")
                val defaultIndex = "token"

                val filtered = fullValidCorpusConfig.filterBy(requirement, defaultIndex)

                val expected = corpusConfig("CC") {
                    indexes {
                        index("token") {
                            columnIndex = 1
                            description = "Original word in the document"
                        }
                        entityMapping {
                            entityIndex = "nertag"
                            attributeIndexes = 15 to 24
                            extraIndexes("nertype", "nerlength")
                        }
                    }
                }
                assertThat(filtered)
                        .isEqualTo(expected)
            }

            @Test
            fun `toplevel predefined all`() {
                val requirement = TextMetadata.Predefined("all")
                val defaultIndex = "token"

                val filtered = fullValidCorpusConfig.filterBy(requirement, defaultIndex)
                assertThat(filtered)
                        .isEqualTo(fullValidCorpusConfig)
            }

            @Test
            fun `exact all indexes no entities`() {
                val requirement = TextMetadata.ExactDefinition(
                        indexes = Indexes.Predefined("all"),
                        entities = Entities.Predefined("none")
                )
                val defaultIndex = "token"

                val filtered = fullValidCorpusConfig.filterBy(requirement, defaultIndex)

                val expected = corpusConfig("CC") {
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
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = 15 to 24
                        extraIndexes("nertype", "nerlength")
                    }
                }
                assertThat(filtered)
                        .isEqualTo(expected)
            }

            @Test
            fun `exact no indexes no entities`() {
                val requirement = TextMetadata.ExactDefinition(
                        indexes = Indexes.Predefined("none"),
                        entities = Entities.Predefined("none")
                )
                val defaultIndex = "token"

                val filtered = fullValidCorpusConfig.filterBy(requirement, defaultIndex)

                val expected = corpusConfig("CC") {
                    indexes {
                        index("token") {
                            columnIndex = 1
                            description = "Original word in the document"
                        }
                    }
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = 15 to 24
                        extraIndexes("nertype", "nerlength")
                    }
                }
                assertThat(filtered)
                        .isEqualTo(expected)
            }

            @Test
            fun `exact three indexes no entities`() {
                val requirement = TextMetadata.ExactDefinition(
                        indexes = Indexes.ExactDefinition(listOf("lemma", "nerlength")),
                        entities = Entities.Predefined("none")
                )
                val defaultIndex = "token"

                val filtered = fullValidCorpusConfig.filterBy(requirement, defaultIndex)

                val expected = corpusConfig("CC") {
                    indexes {
                        index("token") {
                            columnIndex = 1
                            description = "Original word in the document"
                        }
                        index("lemma") {
                            columnIndex = 3
                            description = "Lemma of the word"
                        }
                        index("nerlength") {
                            columnIndex = 26
                            description = "nerlength"
                        }
                    }
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = 15 to 24
                        extraIndexes("nertype", "nerlength")
                    }
                }
                assertThat(filtered)
                        .isEqualTo(expected)
            }

            @Test
            fun `exact two indexes one entity with no attributes`() {
                val requirement = TextMetadata.ExactDefinition(
                        indexes = Indexes.ExactDefinition(listOf("nerlength")),
                        entities = Entities.ExactDefinition(
                                mapOf("museum" to Indexes.Predefined("none"))
                        )
                )
                val defaultIndex = "token"

                val filtered = fullValidCorpusConfig.filterBy(requirement, defaultIndex)

                val expected = corpusConfig("CC") {
                    indexes {
                        index("token") {
                            columnIndex = 1
                            description = "Original word in the document"
                        }
                        index("nerlength") {
                            columnIndex = 26
                            description = "nerlength"
                        }
                    }
                    entities {
                        entity("museum")
                    }
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = 15 to 24
                        extraIndexes("nertype", "nerlength")
                    }
                }
                assertThat(filtered)
                        .isEqualTo(expected)
            }


            @Test
            fun `exact two indexes one entity with all attributes`() {
                val requirement = TextMetadata.ExactDefinition(
                        indexes = Indexes.ExactDefinition(listOf("nerlength")),
                        entities = Entities.ExactDefinition(
                                mapOf("artist" to Indexes.Predefined("all"))
                        )
                )
                val defaultIndex = "token"

                val filtered = fullValidCorpusConfig.filterBy(requirement, defaultIndex)

                val expected = corpusConfig("CC") {
                    indexes {
                        index("token") {
                            columnIndex = 1
                            description = "Original word in the document"
                        }
                        index("nerlength") {
                            columnIndex = 26
                            description = "nerlength"
                        }
                    }
                    entities {
                        "artist" with attributes("url", "image", "name", "gender", "birthplace", "birthdate", "deathplace", "deathdate", "role", "nationality")
                    }
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = 15 to 24
                        extraIndexes("nertype", "nerlength")
                    }
                }
                assertThat(filtered)
                        .isEqualTo(expected)
            }

            @Test
            fun `exact two indexes one entity with specified attributes`() {
                val requirement = TextMetadata.ExactDefinition(
                        indexes = Indexes.ExactDefinition(listOf("nerlength")),
                        entities = Entities.ExactDefinition(
                                mapOf("event" to Indexes.ExactDefinition(listOf("url", "name", "startdate", "location")))
                        )
                )
                val defaultIndex = "token"

                val filtered = fullValidCorpusConfig.filterBy(requirement, defaultIndex)

                val expected = corpusConfig("CC") {
                    indexes {
                        index("token") {
                            columnIndex = 1
                            description = "Original word in the document"
                        }
                        index("nerlength") {
                            columnIndex = 26
                            description = "nerlength"
                        }
                    }
                    entities {
                        entity("event") {
                            attributes {
                                attribute("url")
                                attribute("name") {
                                    columnIndex = 2
                                }
                                attribute("startdate") {
                                    columnIndex = 3
                                }
                                attribute("location") {
                                    columnIndex = 5
                                }
                            }
                        }
                    }
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = 15 to 24
                        extraIndexes("nertype", "nerlength")
                    }
                }
                assertThat(filtered)
                        .isEqualTo(expected)
            }

            @Test
            fun `exact two indexes one entity with specified attributes one entity with all one with none`() {
                val requirement = TextMetadata.ExactDefinition(
                        indexes = Indexes.ExactDefinition(listOf("nerlength")),
                        entities = Entities.ExactDefinition(
                                mapOf("event" to Indexes.ExactDefinition(listOf("url", "name", "startdate", "location")),
                                        "person" to Indexes.Predefined("all"),
                                        "form" to Indexes.Predefined("none")
                                ))
                )
                val defaultIndex = "token"

                val filtered = fullValidCorpusConfig.filterBy(requirement, defaultIndex)

                val expected = corpusConfig("CC") {
                    indexes {
                        index("token") {
                            columnIndex = 1
                            description = "Original word in the document"
                        }
                        index("nerlength") {
                            columnIndex = 26
                            description = "nerlength"
                        }
                    }
                    entities {
                        entity("event") {
                            attributes {
                                attribute("url")
                                attribute("name") {
                                    columnIndex = 2
                                }
                                attribute("startdate") {
                                    columnIndex = 3
                                }
                                attribute("location") {
                                    columnIndex = 5
                                }
                            }
                        }
                        "person" with attributes("url", "image", "name", "gender", "birthplace", "birthdate", "deathplace", "deathdate", "profession", "nationality")
                        entity("form")
                    }
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = 15 to 24
                        extraIndexes("nertype", "nerlength")
                    }
                }
                assertThat(filtered)
                        .isEqualTo(expected)
            }


        }

        private val fullValidCorpusConfig = corpusConfig("CC") {
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
            entityMapping {
                entityIndex = "nertag"
                attributeIndexes = 15 to 24
                extraIndexes("nertype", "nerlength")
            }
        }

        @Nested
        inner class Validation {

            @Test
            fun `valid config`() {
                val errors = fullValidCorpusConfig.validate()
                assertThat(errors)
                        .isEmpty()
            }


            @Test
            fun `blank config name`() {
                val config = corpusConfig("  ") {
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
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = 15 to 24
                        extraIndexes("nertype", "nerlength")
                    }
                }
                val errors = config.validate()
                assertThat(errors)
                        .contains("Corpus name should neither be empty nor blank")
            }

            @Test
            fun `entity index not found`() {
                val config = corpusConfig("CC") {
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
                    entityMapping {
                        entityIndex = "nertagg"
                        attributeIndexes = 15 to 24
                        extraIndexes("nertype", "nerlength")
                    }
                }
                val errors = config.validate()
                assertThat(errors)
                        .contains("entity index nertagg not found")
            }

            @Test
            fun `attribute indexes range is not within indexes size`() {
                val config = corpusConfig("CC") {
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
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = -1 to 50
                        extraIndexes("nertype", "nerlength")
                    }
                }
                val errors = config.validate()
                assertThat(errors)
                        .contains("attribute index lower bound -1 is not within 0..26",
                                "attribute index upper bound 50 is not within 0..26")
            }

            @Test
            fun `missing extra index`() {
                val config = corpusConfig("CC") {
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
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = 15 to 24
                        extraIndexes("nertype", "nerlengtg")
                    }
                }
                val errors = config.validate()
                assertThat(errors)
                        .contains("extra index nerlengtg was not found within indexes")
            }

            @Test
            fun `entity has too many attributes`() {
                val config = corpusConfig("CC") {
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
                        "person" with attributes("url", "image", "name", "gender", "birthplace", "birthdate", "deathplace", "deathdate", "profession", "nationality", "oneTooMany")

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
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = 15 to 24
                        extraIndexes("nertype", "nerlength")
                    }
                }
                val errors = config.validate()
                assertThat(errors)
                        .contains("entity person has too many attributes, [oneTooMany] are above the specified size 10(range 15..24)")
            }
        }
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
                        entityMapping {
                            entityIndex = "nertag"
                            attributeIndexes = 15 to 24
                            extraIndexes("nertype", "nerlength")
                        }
                    }


                }
                searchConfig {
                    snippetCount = 42
                }
            }

            val expected = ConsoleClientConfig()
            expected.searchConfig = SearchConfig(42)
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
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = 15 to 24
                        extraIndexes("nertype", "nerlength")
                    }
                }

            })

            assertThat(config)
                    .isEqualTo(expected)
        }


        @Test
        fun `remote client`() {
            val config = consoleClient {
                remote {
                    servers("localhost:8001", "localhost:8001", "localhost:8003")
                }
                searchConfig {
                    snippetCount = 42
                    defaultIndex = "lemma"
                    responseFormat = ResponseFormat.HTML
                    responseType = ResponseType.IDENTIFIER_LIST
                }
            }

            val servers = listOf("localhost:8001", "localhost:8001", "localhost:8003").map { ServerInfo(it) }.toMutableList()
            val expected = ConsoleClientConfig()
            expected.searchConfig = SearchConfig(42, TextMetadata.Predefined("all"), ResponseType.IDENTIFIER_LIST, ResponseFormat.HTML, "lemma")
            expected.clientType = ConsoleClientType.RemoteIndex(servers)
            assertThat(config)
                    .isEqualTo(expected)
        }
    }
}