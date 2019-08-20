package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.config.SearchConfig
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
                        attribute("name") {
                            description = "The name of the person"
                        }
                    }
                }
            }
            assertThat(entities)
                    .isEqualTo(mutableMapOf(
                            "person" to Entity("person", "Person entity", mutableMapOf("name" to Attribute("name", description = "The name of the person", attributeIndex = 0)))
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
                            attribute("name") {
                                description = "The name of the person"
                            }
                        }
                    }
                }
                entityMapping {
                    entityIndex = "nertag"
                    attributeIndexes = 15 to 24
                    extraAttributes("nertype", "nerlength")
                }
            }
            assertThat(corpus)
                    .isEqualTo(CorpusConfiguration("wiki2018",
                            indexes = mutableMapOf(
                                    "token" to Index("token", "Original token from the document"),
                                    "lemma" to Index("lemma", "The lemma of the word", columnIndex = 1)
                            ),
                            entities = mutableMapOf(
                                    "person" to Entity("person", "Person entity", mutableMapOf("name" to Attribute("name", description = "The name of the person", attributeIndex = 0)))
                            ),
                            entityMapping = EntityMapping().apply {
                                entityIndex = "nertag"
                                attributeIndexes = 15 to 24
                                extraAttributes = LinkedHashSet(listOf("nertype", "nerlength"))
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
                        index("nertag")
                        params(2)
                        index("nerlength")
                    }
                    entities {
                        entity("person") {
                            description = "Person entity"
                            attributes {
                                attribute("name") {
                                    description = "The name of the person"
                                }
                            }
                        }
                        entity("artist") {
                            attributes("name", "gender")
                        }
                        "date" with attributes("year", "month", "day")
                    }
                    entityMapping {
                        attributeIndexes = 4 to 6
                    }
                }
            }
            assertThat(config.validate()).isEmpty()
            val expected = IndexBuilderConfig().apply {
                input = listOf(1, 2, 3).map { "../data/mg4j/cc$it.mg4j" }.map { File(it) }
                output = File("../data/indexed")
                corpusConfiguration = CorpusConfiguration("wiki2018",
                        indexes = mutableMapOf(
                                "token" to Index("token", "Original token from the document"),
                                "lemma" to Index("lemma", "The lemma of the word", columnIndex = 1),
                                "tag" to Index("tag", "Tag of the word", columnIndex = 2),
                                "nertag" to Index("nertag", "", columnIndex = 3),
                                "param0" to Index("param0", "", columnIndex = 4),
                                "param1" to Index("param1", "", columnIndex = 5),
                                "param2" to Index("param2", "", columnIndex = 6),
                                "nerlength" to Index("nerlength", "", columnIndex = 7)
                        ),
                        entities = mutableMapOf(
                                "person" to Entity("person", "Person entity", mutableMapOf("name" to Attribute("name", description = "The name of the person", correspondingIndex = "param0", attributeIndex = 0, columnIndex = 4))),
                                "artist" to Entity("artist", "", mutableMapOf("name" to Attribute("name", correspondingIndex = "param0", columnIndex = 4, attributeIndex = 0), "gender" to Attribute("gender", correspondingIndex = "param1", columnIndex = 5, attributeIndex = 1))),
                                "date" to Entity("date", "", mutableMapOf("year" to Attribute("year", correspondingIndex = "param0", columnIndex = 4, attributeIndex = 0), "month" to Attribute("month", correspondingIndex = "param1", columnIndex = 5, attributeIndex = 1), "day" to Attribute("day", correspondingIndex = "param2", columnIndex = 6, attributeIndex = 2)))
                        ),
                        entityMapping = EntityMapping().apply {
                            attributeIndexes = 4 to 6
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
                            extraAttributes("nertype", "nerlength")
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
                        extraAttributes("nertype", "nerlength")
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
                        extraAttributes("nertype", "nerlength")
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
                        extraAttributes("nertype", "nerlength")
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
                        extraAttributes("nertype", "nerlength")
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
                        entity("artist") {
                            attributes {
                                attribute("url") {
                                    columnIndex = 15
                                    correspondingIndex = "param0"
                                }
                                attribute("image") {
                                    columnIndex = 16
                                    correspondingIndex = "param1"
                                }
                                attribute("name") {
                                    columnIndex = 17
                                    correspondingIndex = "param2"
                                }
                                attribute("gender") {
                                    columnIndex = 18
                                    correspondingIndex = "param3"
                                }
                                attribute("birthplace") {
                                    columnIndex = 19
                                    correspondingIndex = "param4"
                                }
                                attribute("birthdate") {
                                    columnIndex = 20
                                    correspondingIndex = "param5"
                                }
                                attribute("deathplace") {
                                    columnIndex = 21
                                    correspondingIndex = "param6"
                                }
                                attribute("deathdate") {
                                    columnIndex = 22
                                    correspondingIndex = "param7"
                                }
                                attribute("role") {
                                    columnIndex = 23
                                    correspondingIndex = "param8"
                                }
                                attribute("nationality") {
                                    columnIndex = 24
                                    correspondingIndex = "param9"
                                }
                                attribute("nertype") {
                                    columnIndex = 25
                                    correspondingIndex = "nertype"
                                    description = "nertype"
                                }
                                attribute("nerlength") {
                                    columnIndex = 26
                                    correspondingIndex = "nerlength"
                                    description = "nerlength"
                                }
                            }
                        }
                    }
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = 15 to 24
                        extraAttributes("nertype", "nerlength")
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
                                attribute("url") {
                                    columnIndex = 15
                                    attributeIndex = 0
                                    correspondingIndex = "param0"
                                }
                                attribute("name") {
                                    columnIndex = 17
                                    attributeIndex = 2
                                    correspondingIndex = "param2"
                                }
                                attribute("startdate") {
                                    columnIndex = 18
                                    attributeIndex = 3
                                    correspondingIndex = "param3"
                                }
                                attribute("location") {
                                    columnIndex = 20
                                    attributeIndex = 5
                                    correspondingIndex = "param5"
                                }
                            }
                        }
                    }
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = 15 to 24
                        extraAttributes("nertype", "nerlength")
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
                                attribute("url") {
                                    columnIndex = 15
                                    attributeIndex = 0
                                    correspondingIndex = "param0"
                                }
                                attribute("name") {
                                    columnIndex = 17
                                    attributeIndex = 2
                                    correspondingIndex = "param2"
                                }
                                attribute("startdate") {
                                    columnIndex = 18
                                    attributeIndex = 3
                                    correspondingIndex = "param3"
                                }
                                attribute("location") {
                                    columnIndex = 20
                                    attributeIndex = 5
                                    correspondingIndex = "param5"
                                }
                            }
                        }
                        entity("person") {
                            attributes {
                                attribute("url") {
                                    columnIndex = 15
                                    attributeIndex = 0
                                    correspondingIndex = "param0"
                                }
                                attribute("image") {
                                    columnIndex = 16
                                    attributeIndex = 1
                                    correspondingIndex = "param1"
                                }
                                attribute("name") {
                                    columnIndex = 17
                                    attributeIndex = 2
                                    correspondingIndex = "param2"
                                }
                                attribute("gender") {
                                    columnIndex = 18
                                    attributeIndex = 3
                                    correspondingIndex = "param3"
                                }
                                attribute("birthplace") {
                                    columnIndex = 19
                                    attributeIndex = 4
                                    correspondingIndex = "param4"
                                }
                                attribute("birthdate") {
                                    columnIndex = 20
                                    attributeIndex = 5
                                    correspondingIndex = "param5"
                                }
                                attribute("deathplace") {
                                    columnIndex = 21
                                    attributeIndex = 6
                                    correspondingIndex = "param6"
                                }
                                attribute("deathdate") {
                                    columnIndex = 22
                                    attributeIndex = 7
                                    correspondingIndex = "param7"
                                }
                                attribute("profession") {
                                    columnIndex = 23
                                    attributeIndex = 8
                                    correspondingIndex = "param8"
                                }
                                attribute("nationality") {
                                    columnIndex = 24
                                    attributeIndex = 9
                                    correspondingIndex = "param9"
                                }
                                attribute("nertype") {
                                    columnIndex = 25
                                    correspondingIndex = "nertype"
                                    description = "nertype"
                                }
                                attribute("nerlength") {
                                    columnIndex = 26
                                    correspondingIndex = "nerlength"
                                    description = "nerlength"
                                }
                            }
                        }
                        entity("form")
                    }
                    entityMapping {
                        entityIndex = "nertag"
                        attributeIndexes = 15 to 24
                        extraAttributes("nertype", "nerlength")
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
                extraAttributes("nertype", "nerlength")
            }
        }.also {
            assertThat(it.validate())
                    .isEmpty()
        }

        @Nested
        inner class Validation {

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
                        extraAttributes("nertype", "nerlength")
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
                        extraAttributes("nertype", "nerlength")
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
                        extraAttributes("nertype", "nerlength")
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
                        extraAttributes("nertype", "nerlengtg")
                    }
                }
                val errors = config.validate()
                assertThat(errors)
                        .contains("extra attribute nerlengtg was not found within indexes")
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
                        extraAttributes("nertype", "nerlength")
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
                    collections {
                        collection("one") {
                            mg4jDirectory("../data/mg4j")
                            indexDirectory("../data/indexed")
                        }
                    }

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
                            extraAttributes("nertype", "nerlength")
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
                collections {
                    collection("one") {
                        mg4jDirectory("../data/mg4j")
                        indexDirectory("../data/indexed")
                    }
                }

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
                        extraAttributes("nertype", "nerlength")
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
                    textFormat = TextFormat.HTML
                    resultFormat = ResultFormat.IDENTIFIER_LIST
                }
            }

            val servers = listOf("localhost:8001", "localhost:8001", "localhost:8003").toMutableList()
            val expected = ConsoleClientConfig()
            expected.searchConfig = SearchConfig(42, TextMetadata.Predefined("all"), ResultFormat.IDENTIFIER_LIST, TextFormat.HTML, "lemma")
            expected.clientType = ConsoleClientType.RemoteIndex(servers)
            assertThat(config)
                    .isEqualTo(expected)
        }
    }
}