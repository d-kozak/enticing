package cz.vutbr.fit.knot.enticing.indexer

import cz.vutbr.fit.knot.enticing.dto.config.dsl.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File


val DUMMY_LOAD_CONFIGURATION: (path: String) -> IndexBuilderConfig = {
    indexBuilder {
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
}

class HandleArgumentsTest {

    @Test
    fun `At least four arguments necessary`() {
        assertThrows<IllegalArgumentException> {
            handleArguments(loadConfig = DUMMY_LOAD_CONFIGURATION)
        }
        assertThrows<IllegalArgumentException> {
            handleArguments("foo/config.kts", loadConfig = DUMMY_LOAD_CONFIGURATION)
        }
        assertThrows<IllegalArgumentException> {
            handleArguments("foo/config.kts", "../data/mg4j", loadConfig = DUMMY_LOAD_CONFIGURATION)
        }
        assertThrows<IllegalArgumentException> {
            handleArguments("foo/config.kts", "../data/mg4j", "../data/indexed", loadConfig = DUMMY_LOAD_CONFIGURATION)
        }
    }

    @Test
    fun `Loading of dsl requested based on the path in the first argument`() {
        var calledWith: String? = null
        val load: (String) -> IndexBuilderConfig = { path ->
            calledWith != null && throw IllegalStateException("Called with param is set, which suggest that load was called more than once")
            calledWith = path
            DUMMY_LOAD_CONFIGURATION(path)
        }
        handleArguments("foo/bar/baz/config.kts", "col1", "../data/mg4j", "../data/indexed", loadConfig = load)

        assertThat(calledWith)
                .isNotNull()
                .isEqualTo("foo/bar/baz/config.kts")
    }

    @Test
    fun `Exactly specified input mgj4 files`() {
        val config = handleArguments("foo/bar/baz/config.kts", "col1", "../data/mg4j/cc1.mg4j", "../data/mg4j/cc2.mg4j", "../data/indexed", loadConfig = DUMMY_LOAD_CONFIGURATION)
        assertThat(config.input.toSet())
                .isEqualTo(setOf(File("../data/mg4j/cc1.mg4j"), File("../data/mg4j/cc2.mg4j")))
        assertThat(config.output)
                .isEqualTo(File("../data/indexed"))
    }

    @Test
    fun `Input specified as directory`() {
        val config = handleArguments("foo/bar/baz/config.kts", "col1", "../data/mg4j", "../data/indexed", loadConfig = DUMMY_LOAD_CONFIGURATION)
        assertThat(config.input.toSet())
                .isEqualTo(setOf(File("../data/mg4j/cc1.mg4j"), File("../data/mg4j/cc2.mg4j"), File("../data/mg4j/cc3.mg4j"), File("../data/mg4j/small.mg4j")))
        assertThat(config.output)
                .isEqualTo(File("../data/indexed"))
    }
}