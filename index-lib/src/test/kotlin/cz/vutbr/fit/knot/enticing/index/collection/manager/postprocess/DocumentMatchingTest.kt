package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.dto.interval.Interval
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


    @DisplayName("(food horse)|(house money)")
    @Test
    fun withOrAndParens() = forEachMatch("(food horse)|(house money)") {
        forEachInterval("one of the disjuncts should be there") {
            val text = textAt("token", interval)
            verify(("food" in text && "horse" in text) || ("house" in text && "money" in text)) { "('food' and 'horse') or ('house' and 'money') should be in '$text'" }
        }

        forEachLeaf("should be one of: food horse house money") {
            val text = textAt("token", leafMatch.match)
            val options = setOf("food", "horse", "house", "money")
            verify(text in options) { "leaf should be one of 'food horse house money', was '$text'" }
        }
    }

    @DisplayName("position:(1 < 5)")
    @Test
    fun positionIndex() = forEachMatch("position:(1 < 5)") {
        forEachInterval("1 and 5 should be there in this order") {
            val text = textAt("position", interval)
            val one = text.indexOf("1")
            val five = text.indexOf("5")
            verify(one >= 0) { "'one' not found in '$text'" }
            verify(five >= 0) { "'five' not found in '$text'" }
            verify(one + "1".length < five) { "'1' should be located before '5' in '$text'" }
        }

        forEachLeaf("should be 1 or 5") {
            val text = textAt("position", leafMatch.match)
            verify(text == "1" || text == "5") { "should be 1 or 5" }
        }
    }

    @DisplayName("lemma:((food horse)|(house money))")
    @Test
    fun indexWithOrAndParens() = forEachMatch("lemma:((food horse)|(house money))") {
        forEachInterval("one of the disjuncts should be there") {
            val text = textAt("lemma", interval)
            verify(("food" in text && "horse" in text) || ("house" in text && "money" in text)) { "('food' and 'horse') or ('house' and 'money') should be in '$text'" }
        }

        forEachLeaf("should be one of: food horse house money") {
            val text = textAt("lemma", leafMatch.match)
            val options = setOf("food", "horse", "house", "money")
            verify(text in options) { "leaf should be one of 'food horse house money', was '$text'" }
        }
    }

    @DisplayName("position:10 lemma:work is")
    @Test
    fun moreComplex() = forEachMatch("position:10 lemma:work is") {
        forEachInterval("should contains all three conjucts") {
            val pos = textAt("position", interval)
            verify(pos.indexOf("10") >= 0) { "pos:'10' was not found in '$pos'" }
            val lemma = textAt("lemma", interval)
            verify(lemma.indexOf("work") >= 0) { "lemma:'work' was not found in '$lemma'" }
            val text = textAt("token", interval)
            verify(text.indexOf("is") >= 0) { "'is' was not found in '$text'" }
        }
    }

    @Test
    @DisplayName("person house ~ 3")
    fun prox() {
        forEachMatch("person house ~ 3") {
            forEachLeaf("person should be close to house") {
                val text = textAt("token", leafMatch.match)
                if (text != "person") return@forEachLeaf true
                val extended = textAt("token", Interval.valueOf(leafMatch.match.to - 4, leafMatch.match.to + 4))
                verify("house" in extended) { "house should be within '$extended'" }
            }
        }
    }


    @Test
    @DisplayName("(person house ~ 3)")
    fun proxInParens() {
        forEachMatch("person house ~ 3") {
            forEachLeaf("person should be close to house") {
                val text = textAt("token", leafMatch.match)
                if (text != "person") return@forEachLeaf true
                val extended = textAt("token", Interval.valueOf(leafMatch.match.to - 4, leafMatch.match.to + 4))
                verify("house" in extended) { "house should be within '$extended'" }
            }
        }
    }


    @DisplayName("(influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist)) - _PAR_ && influencer.url != influencee.url")
    @Test
    fun exampleQuery() = forEachMatch("(influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist)) - _PAR_ && influencer.url != influencee.url") {
        forEachInterval("test") {
            val nertag = textAt("nertag", interval)
            verify("person" in nertag || "artist" in nertag) { "entities of type 'person' or 'artist'  should be found in '$nertag'" }
            val lemma = textAt("lemma", interval)
            verify("influence" in lemma || "impact" in lemma || ("paid" in lemma && "tribute" in lemma)) { "lemma requirement failed" }
            val text = textAt("token", interval)
            verify("§" !in text) { "there should be no paragraphs marks(§) with '$text'" }
        }
    }
}