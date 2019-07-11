package cz.vutbr.fit.knot.enticing.webserver.service.mock

import cz.vutbr.fit.knot.enticing.dto.response.*
import cz.vutbr.fit.knot.enticing.dto.utils.with


val firstResult = Snippet(
        documentId = 0,
        documentTitle = "Ed",
        collection = "col1",
        location = 0,
        size = 42,
        payload = Payload.FullResponse.Annotated(
                AnnotatedText(
                        text = "Ed Sheeran visited Liberia and meets JD, a homeless Liberian 14-year-old boy. After Sheeran saw an older man hitting JD in public, he knew",
                        annotations = mapOf("ed" to EdSheeran),
                        positions = listOf(AnnotationPosition("ed", MatchedRegion(0, 10))),
                        queryMapping = listOf(QueryMapping(textIndex = MatchedRegion(0, 10), queryIndex = MatchedRegion(0, 5)))
                )
        ),
        url = "https://www.borgenmagazine.com/ed-sheeran-visited-liberia/",
        canExtend = true
) with SnippetExtra("server1")

val secondResult = Snippet(
        documentId = 1,
        documentTitle = "donald",
        location = 0,
        collection = "col1",
        size = 42,
        payload = Payload.FullResponse.Annotated(
                AnnotatedText(
                        text = "President Donald Trump visited San Antonio for a closed-door fundraiser at The Argyle, the exclusive dinner club in Alamo Heights. Air Force ...",
                        annotations = mapOf("donald" to DonaldTrump),
                        positions = listOf(AnnotationPosition("donald", MatchedRegion(10, 12))),
                        queryMapping = listOf(QueryMapping(MatchedRegion(0, 16), MatchedRegion(0, 5)))
                )
        ),
        url = "https://www.mysanantonio.com/news/local/article/President-Trump-arrives-in-San-Antonio-for-13756986.php",
        canExtend = true
) with SnippetExtra("server2")

val thirdResult = Snippet(
        documentId = 2,
        documentTitle = "milos",
        location = 0,
        collection = "col1",
        size = 42,
        payload = Payload.FullResponse.Annotated(
                AnnotatedText(
                        text = "The president of the Czech republic Milos Zeman visited a porcelain factory Thun 1794 within his two-day visit to Karlovy Vary region. The president met with ...",
                        annotations = mapOf("kv" to KarlovyVary),
                        positions = listOf(AnnotationPosition("kv", MatchedRegion(114, 13))),
                        queryMapping = listOf(QueryMapping(MatchedRegion(117, 23), MatchedRegion(0, 5)))
                )
        ),
        url = "https://www.thun.cz/en/article/238-visit-of-mr--president-milos-zeman.html",
        canExtend = true
) with SnippetExtra("server3")

val results = listOf(firstResult, secondResult, thirdResult)
fun randomResult() = results[Math.floor(Math.random() * results.size).toInt()]

val allResults = Array(50) { randomResult() }.toList()

