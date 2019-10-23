package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.index.corpusConfig
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


internal class DocumentMatchingTest : AbstractDocumentMatchingTest() {


    @DisplayName("food")
    @Test
    fun one() = forEachMatch("food") {
        interval("should contain food") { document, interval ->
            document.content[corpusConfig.indexes.getValue("token").columnIndex][interval.from].toLowerCase() == "food"
        }
        leaf("should contain food") { document, match ->
            document.content[corpusConfig.indexes.getValue("token").columnIndex][match.match.from].toLowerCase() == "food"
        }
    }


    @DisplayName("That Motion three")
    @Test
    fun simpleQuery() = forEachMatch("That Motion three") {
        interval("all three words should be there") { document, interval ->
            val text = document.content[corpusConfig.indexes.getValue("token").columnIndex].subList(interval.from, interval.to + 1).joinToString(" ") { it.toLowerCase() }
            "that" in text && "motion" in text && "three" in text
        }
    }


    @Test
    @DisplayName("th*")
    fun withStar() = forEachMatch("th*") {
        leaf("should start with th") { document, match ->
            val text = document.content[corpusConfig.indexes.getValue("token").columnIndex][match.match.from]
            text.toLowerCase().startsWith("th")
        }
    }

    @Test
    @DisplayName("person.name:Mic*")
    fun person() = forEachMatch("person.name:Mic*") {
        interval("should be person with name like Mic...") { document, interval ->
            val nertag = document.content[corpusConfig.indexes.getValue("nertag").columnIndex]
            val name = document.content[corpusConfig.entities.getValue("person").attributes.getValue("name").columnIndex]
            var success = true
            for (i in interval) {
                if (nertag[i] != "person") success = false
                if (!name[i].toLowerCase().startsWith("mic")) success = false
            }
            success
        }
    }


    @Test
    @DisplayName("That < Motion")
    fun next() = forEachMatch("That < Motion") {
        interval("that should be before motion") { document, interval ->
            val text = document.content[corpusConfig.indexes.getValue("token").columnIndex].subList(interval.from, interval.to + 1).joinToString(" ") { it.toLowerCase() }
            val that = text.indexOf("that")
            val motion = text.indexOf("motion")
            that >= 0 && motion >= 0 && that + "that".length < motion
        }

        leaf("leaves should be either that or motion") { document, match ->
            val text = document.content[corpusConfig.indexes.getValue("token").columnIndex][match.match.from].toLowerCase()
            text == "that" || text == "motion"
        }
    }

}