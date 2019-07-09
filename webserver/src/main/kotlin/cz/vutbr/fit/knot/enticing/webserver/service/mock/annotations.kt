package cz.vutbr.fit.knot.enticing.webserver.service.mock

import cz.vutbr.fit.knot.enticing.dto.response.Annotation

val EdSheeran = Annotation(
        id = 1,
        content = mapOf(
                "type" to "person",
                "image" to "https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTM5ODkxNzYyODU1NDIwOTM4/ed-sheeran-gettyimages-494227430_1600jpg.jpg",
                "name" to "Ed Sheeran",
                "url" to "https://cs.wikipedia.org/wiki/Ed_Sheeran",
                "gender" to "male",
                "birthplace" to "Halifax, England",
                "birthdate" to "17.2.1991"
        )

)

val DonaldTrump = Annotation(
        id = 2,
        content = mapOf(
                "type" to "person",
                "image" to "https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Donald_Trump_official_portrait.jpg/800px-Donald_Trump_official_portrait.jpg",
                "name" to "Donald Trump",
                "url" to "https://cs.wikipedia.org/wiki/Donald_Trump",
                "gender" to "male",
                "birthplace" to "Queens, New York",
                "birthdate" to "14.6.1946"
        )
)

val KarlovyVary = Annotation(
        id = 2,
        content = mapOf(
                "type" to "place",
                "image" to "https://upload.wikimedia.org/wikipedia/commons/d/d8/Karlovy_Vary_Czech.jpg",
                "name" to "Karlovy Vary",
                "url" to "https://cs.wikipedia.org/wiki/Karlovy_Vary",
                "country" to "Czechia"
        )
)