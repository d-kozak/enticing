package cz.vutbr.fit.knot.enticing.webserver.service.mock

import cz.vutbr.fit.knot.enticing.dto.response.*


val firstResult = Match(
        document = 0,
        collection = "col1",
        location = 0,
        size = 42,
        payload = Payload.Snippet.Json(
                AnnotatedText(
                        text = "Ed Sheeran visited Liberia and meets JD, a homeless Liberian 14-year-old boy. After Sheeran saw an older man hitting JD in public, he knew",
                        annotations = mapOf(1L to EdSheeran),
                        positions = listOf(AnnotationPosition(1, 0, 10)),
                        queryMapping = listOf(QueryMapping(0, 10, "nertag:person"))
                )
        ),
        url = "https://www.borgenmagazine.com/ed-sheeran-visited-liberia/",
        canExtend = true
)

val secondResult = Match(
        document = 1,
        location = 0,
        collection = "col1",
        size = 42,
        payload = Payload.Snippet.Json(
                AnnotatedText(
                        text = "President Donald Trump visited San Antonio for a closed-door fundraiser at The Argyle, the exclusive dinner club in Alamo Heights. Air Force ...",
                        annotations = mapOf(2L to DonaldTrump),
                        positions = listOf(AnnotationPosition(2, 10, 22)),
                        queryMapping = listOf(QueryMapping(0, 16, "nertag:person"))
                )
        ),
        url = "https://www.mysanantonio.com/news/local/article/President-Trump-arrives-in-San-Antonio-for-13756986.php",
        canExtend = true
)

val thirdResult = Match(
        document = 2,
        location = 0,
        collection = "col1",
        size = 42,
        payload = Payload.Snippet.Json(
                AnnotatedText(
                        text = "The president of the Czech republic Milos Zeman visited a porcelain factory Thun 1794 within his two-day visit to Karlovy Vary region. The president met with ...",
                        annotations = mapOf(3L to KarlovyVary),
                        positions = listOf(AnnotationPosition(3, 114, 127)),
                        queryMapping = listOf(QueryMapping(117, 140, "nertag:place"))
                )
        ),
        url = "https://www.thun.cz/en/article/238-visit-of-mr--president-milos-zeman.html",
        canExtend = true
)

val results = listOf(firstResult, secondResult, thirdResult)
fun randomResult() = results[Math.floor(Math.random() * results.size).toInt()]

val allResults = Array(50) { randomResult().copy(canExtend = Math.random() > 0.3) }.toList()

