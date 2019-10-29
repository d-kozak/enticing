package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class GlobalConstraintsTest : AbstractDocumentMatchingTest() {

    @DisplayName("one:=food two:=food && one != two")
    @Test
    fun one() = forEachMatch("one:=food two:=food && one != two") {
        forEachIdentifier("one") {
            val text = textAt("token", leafMatch.match)
            verify(text == "food") { "text for one should be food, was '$text'" }
        }

        forEachIdentifier("two") {
            val text = textAt("token", leafMatch.match)
            verify(text == "food") { "text for one should be food, was '$text'" }
        }
        verifyGlobalConstraint("one should be different than two") {
            val one = identifiers["one"] ?: return@verifyGlobalConstraint checkFailed("one was not matched")
            val two = identifiers["two"] ?: return@verifyGlobalConstraint checkFailed("two was not matched")
            verify(one.match != two.match) { " one and two should represent different intervals even though the word is the same, they were one == ${one.match}, two == ${two.match}" }
        }
    }

    @DisplayName("one:=food two:=food && one == two")
    @Test
    fun oneOne() = forEachMatch("one:=food two:=food && one != two") {
        forEachIdentifier("one") {
            val text = textAt("token", leafMatch.match)
            verify(text == "food") { "text for one should be food, was '$text'" }
        }

        forEachIdentifier("two") {
            val text = textAt("token", leafMatch.match)
            verify(text == "food") { "text for one should be food, was '$text'" }
        }
        verifyGlobalConstraint("one should be different than two") {
            val one = identifiers["one"] ?: return@verifyGlobalConstraint checkFailed("one was not matched")
            val two = identifiers["two"] ?: return@verifyGlobalConstraint checkFailed("two was not matched")
            verify(one.match == two.match) { " one and two should represent the smae interval, they were one == ${one.match}, two == ${two.match}" }
        }
    }

    @DisplayName("one:=lemma:work two:=lemma:work && one != two")
    @Test
    fun two() = forEachMatch("one:=lemma:work two:=lemma:work && one != two") {
        verifyGlobalConstraint("one should be different than two") {
            val one = identifiers["one"] ?: return@verifyGlobalConstraint checkFailed("one was not matched")
            val two = identifiers["two"] ?: return@verifyGlobalConstraint checkFailed("two was not matched")
            verify(one.match != two.match) { " one and two should represent different intervals even though the word is the same, they were one == ${one.match}, two == ${two.match}" }
        }
    }

}