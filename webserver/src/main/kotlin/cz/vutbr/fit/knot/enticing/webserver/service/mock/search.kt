package cz.vutbr.fit.knot.enticing.webserver.service.mock

import cz.vutbr.fit.knot.enticing.webserver.dto.AnnotatedText
import cz.vutbr.fit.knot.enticing.webserver.dto.AnnotationPosition
import cz.vutbr.fit.knot.enticing.webserver.dto.SearchResult


val firstResult = SearchResult(
        docId = 0,
        location = 0,
        size = 42,
        snippet = AnnotatedText(
                text = "Ed Sheeran visited Liberia and meets JD, a homeless Liberian 14-year-old boy. After Sheeran saw an older man hitting JD in public, he knew",
                annotations = mapOf(1L to EdSheeran),
                positions = listOf(AnnotationPosition(1, 0, 10)),
                queryMapping = emptyList()
        ),
        url = "https://www.borgenmagazine.com/ed-sheeran-visited-liberia/",
        canExtend = true
)

val secondResult = SearchResult(
        docId = 1,
        location = 0,
        size = 42,
        snippet = AnnotatedText(
                text = "President Donald Trump visited San Antonio for a closed-door fundraiser at The Argyle, the exclusive dinner club in Alamo Heights. Air Force ...",
                annotations = mapOf(2L to DonaldTrump),
                positions = listOf(AnnotationPosition(2, 10, 22)),
                queryMapping = emptyList()
        ),
        url = "https://www.mysanantonio.com/news/local/article/President-Trump-arrives-in-San-Antonio-for-13756986.php",
        canExtend = true
)

val thirdResult = SearchResult(
        docId = 2,
        location = 0,
        size = 42,
        snippet = AnnotatedText(
                text = "The president of the Czech republic Milos Zeman visited a porcelain factory Thun 1794 within his two-day visit to Karlovy Vary region. The president met with ...",
                annotations = mapOf(3L to KarlovyVary),
                positions = listOf(AnnotationPosition(3, 114, 127)),
                queryMapping = emptyList()
        ),
        url = "https://www.thun.cz/en/article/238-visit-of-mr--president-milos-zeman.html",
        canExtend = true
)

val results = listOf(firstResult, secondResult, thirdResult)
fun randomResult() = results[Math.floor(Math.random() * results.size).toInt()]

val allResults = Array(50) { randomResult().copy(canExtend = Math.random() > 0.3) }.toList()

