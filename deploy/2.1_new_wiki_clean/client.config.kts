import cz.vutbr.fit.knot.enticing.dto.config.dsl.*

indexClient {
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
            "_glue" whichIs "Contains information whether words in the default index should be separated by spaces or not"
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