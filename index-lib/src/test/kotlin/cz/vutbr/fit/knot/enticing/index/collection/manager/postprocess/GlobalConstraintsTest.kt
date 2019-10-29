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
        verifyGlobalConstraint("one should be different than two", "one", "two") {
            val one = identifiers["one"] ?: return@verifyGlobalConstraint checkFailed("one was not matched")
            val two = identifiers["two"] ?: return@verifyGlobalConstraint checkFailed("two was not matched")
            verify(one.match != two.match) { " one and two should represent different intervals even though the word is the same, they were one == ${one.match}, two == ${two.match}" }
        }
    }

    @DisplayName("influencer:=nertag:(person) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person) - _PAR_ && influencer.url != influencee.url")
    @Test
    fun nestedSimple() = forEachMatch("influencer:=nertag:(person) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person) - _PAR_ && influencer.url != influencee.url") {
        verifyGlobalConstraint("one should be different than two", "influencer", "influencee") {
            val one = identifiers["influencer"]
                    ?: return@verifyGlobalConstraint checkFailed("influencer was not matched")
            val two = identifiers["influencee"]
                    ?: return@verifyGlobalConstraint checkFailed("influencee was not matched")

            val influencer = attributeCellsAt("person", "url", one.match).toSet()
            check(influencer.size == 1) { "inlfuencer, size should be one" }
            val influencee = attributeCellsAt("person", "url", two.match).toSet()
            check(influencee.size == 1) { "influencee, size should be one" }
            verify(influencer != influencee) { " influencer and influencee shoudl have different urls, they had $influencer vs $influencee" }
        }
    }

    @DisplayName("influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist) - _PAR_ && influencer.url != influencee.url")
    @Test
    fun nestedPersonArtist() = forEachMatch("influencer:=nertag:(person|artist) < ( lemma:(influence|impact) | (lemma:paid < lemma:tribute) )  < influencee:=nertag:(person|artist) - _PAR_ && influencer.url != influencee.url") {
        verifyGlobalConstraint("one should be different than two", "influencer", "influencee") {
            val one = identifiers["influencer"]
                    ?: return@verifyGlobalConstraint checkFailed("influencer was not matched")
            val two = identifiers["influencee"]
                    ?: return@verifyGlobalConstraint checkFailed("influencee was not matched")

            val influencer = multipleEntityAttributeCellsAt(setOf("person", "artist"), "url", one.match).toSet()
            check(influencer.size == 1) { "inlfuencer, size should be one" }
            val influencee = multipleEntityAttributeCellsAt(setOf("person", "artist"), "url", two.match).toSet()
            check(influencee.size == 1) { "influencee, size should be one" }
            verify(influencer != influencee) { " influencer and influencee shoudl have different urls, they had $influencer vs $influencee" }
        }
    }




}