package cz.vutbr.fit.knot.enticing.dto.response

import cz.vutbr.fit.knot.enticing.dto.AttributeInfo
import cz.vutbr.fit.knot.enticing.dto.CorpusFormat
import cz.vutbr.fit.knot.enticing.dto.EntityFormat
import cz.vutbr.fit.knot.enticing.dto.config.dsl.*
import cz.vutbr.fit.knot.enticing.dto.toCorpusFormat
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CorpusFormatTest {

    @Test
    fun `simple conversion`() {
        val input = corpusConfig("wiki2018") {
            indexes {
                index("token") {
                    description = "Original token from the document"
                }
                index("lemma") whichIs "The lemma of the word"
                "url" whichIs "url"
                index("nertag")
                index("param0")
                index("len")
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
                lengthIndex = "len"
                attributeIndexes = 4 to 4
                extraAttributes("url")
            }
        }
        assertThat(input.validate())
                .isEmpty()
        val expected = CorpusFormat(
                corpusName = "wiki2018",
                indexes = mapOf(
                        "token" to "Original token from the document",
                        "lemma" to "The lemma of the word",
                        "url" to "url",
                        "nertag" to "",
                        "param0" to "",
                        "len" to ""
                ),
                entities = mapOf(
                        "person" to EntityFormat("Person entity", mapOf(
                                "name" to AttributeInfo("param0", "The name of the person"),
                                "url" to AttributeInfo("url", "url")
                        ))
                ))
        assertThat(input.toCorpusFormat())
                .isEqualTo(expected)
    }
}