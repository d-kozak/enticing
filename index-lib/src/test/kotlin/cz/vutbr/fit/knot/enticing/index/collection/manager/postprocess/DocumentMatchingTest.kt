package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


internal class DocumentMatchingTest : AbstractDocumentMatchingTest() {


    @DisplayName("food")
    @Test
    fun one() = forEachMatch("food") {
        forEachInterval("should contain food") {
            verify(textAt("token", interval) == "food") { "'${textAt("token", interval)}' should be 'food'" }
        }
        forEachLeaf("should contain food") {
            verify(textAt("token", leafMatch.match) == "food") { "'${textAt("token", leafMatch.match)}' should be 'food'" }
        }
    }


    @DisplayName("That Motion three")
    @Test
    fun simpleQuery() = forEachMatch("That Motion three") {
        forEachInterval("all three words should be there") {
            val text = textAt("token", interval)
            verify("that" in text) { "'that' should be in '$text'" }
            verify("motion" in text) { "'motion' should be in '$text'" }
            verify("three" in text) { "'three' should be in '$text'" }
        }
    }

    @Test
    @DisplayName("th*")
    fun withStar() = forEachMatch("th*") {
        forEachLeaf("should start with th") {
            verify(textAt("token", leafMatch.match).startsWith("th")) { "'${textAt("token", leafMatch.match)}' should start with 'th'" }
        }
    }

    @Test
    @DisplayName("person.name:Mic*")
    fun person() = forEachMatch("person.name:Mic*") {
        forEachInterval("should be person with name like Mic...") {
            val nertag = cellsAt("nertag")
            val name = attributeCellsAt("person", "name")
            for (i in interval) {
                verify(nertag[i] == "person") { "entity at index $i should be of type person, was '${nertag[i]}'" }
                verify(name[i].startsWith("mic")) { "person's name at index $i should start was mic , was '${name[i]}'" }
            }
            success
        }
    }

    @Test
    @DisplayName("That < Motion")
    fun next() = forEachMatch("That < Motion") {
        forEachInterval("that should be before motion") {
            val text = textAt("token", interval)
            val that = text.indexOf("that")
            val motion = text.indexOf("motion")
            verify(that >= 0) { "'that' not found in '$text'" }
            verify(motion >= 0) { "'motion' not found in '$text'" }
            verify(that + "that".length < motion) { "'that' should be located before 'motion' in '$text'" }
        }

        forEachLeaf("leaves should be either that or motion") {
            val text = textAt("token", leafMatch.match)
            verify(text == "that" || text == "motion") { "leaf should be either 'that' or 'motion', was '$text'" }
        }

    }

}