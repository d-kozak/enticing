package cz.vutbr.fit.knot.enticing.webserver.service.mock

import cz.vutbr.fit.knot.enticing.dto.SnippetExtension
import cz.vutbr.fit.knot.enticing.dto.Webserver
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat
import cz.vutbr.fit.knot.enticing.dto.format.text.AnnotationPosition
import cz.vutbr.fit.knot.enticing.dto.format.text.MatchedRegion
import cz.vutbr.fit.knot.enticing.dto.format.text.QueryMapping
import cz.vutbr.fit.knot.enticing.dto.format.text.StringWithMetadata

val firstResult = Webserver.SearchResult(
        host = "server1",
        documentId = 0,
        documentTitle = "Ed",
        collection = "col1",
        location = 0,
        size = 42,
        payload = ResultFormat.FullResponse.Annotated(
                StringWithMetadata(
                        text = "Ed Sheeran visited Liberia and meets JD, a homeless Liberian 14-year-old boy. After Sheeran saw an older man hitting JD in public, he knew",
                        annotations = mapOf("ed" to EdSheeran),
                        positions = listOf(AnnotationPosition("ed", MatchedRegion(0, 10))),
                        queryMapping = listOf(QueryMapping(textIndex = MatchedRegion(0, 10), queryIndex = MatchedRegion(0, 5)))
                )
        ),
        url = "https://www.borgenmagazine.com/ed-sheeran-visited-liberia/",
        canExtend = true
)

val secondResult = Webserver.SearchResult(
        host = "server2",
        documentId = 1,
        documentTitle = "donald",
        location = 0,
        collection = "col1",
        size = 42,
        payload = ResultFormat.FullResponse.Annotated(
                StringWithMetadata(
                        text = "President Donald Trump visited San Antonio for a closed-door fundraiser at The Argyle, the exclusive dinner club in Alamo Heights. Air Force ...",
                        annotations = mapOf("donald" to DonaldTrump),
                        positions = listOf(AnnotationPosition("donald", MatchedRegion(10, 12))),
                        queryMapping = listOf(QueryMapping(MatchedRegion(0, 16), MatchedRegion(0, 5)))
                )
        ),
        url = "https://www.mysanantonio.com/news/local/article/President-Trump-arrives-in-San-Antonio-for-13756986.php",
        canExtend = true
)

val thirdResult = Webserver.SearchResult(
        host = "server3",
        documentId = 2,
        documentTitle = "milos",
        location = 0,
        collection = "col1",
        size = 42,
        payload = ResultFormat.FullResponse.Annotated(
                StringWithMetadata(
                        text = "The president of the Czech republic Milos Zeman visited a porcelain factory Thun 1794 within his two-day visit to Karlovy Vary region. The president met with ...",
                        annotations = mapOf("kv" to KarlovyVary),
                        positions = listOf(AnnotationPosition("kv", MatchedRegion(114, 13))),
                        queryMapping = listOf(QueryMapping(MatchedRegion(117, 23), MatchedRegion(0, 5)))
                )
        ),
        url = "https://www.thun.cz/en/article/238-visit-of-mr--president-milos-zeman.html",
        canExtend = true
)

val snippetExtension = SnippetExtension(
        firstResult.payload as ResultFormat.FullResponse,
        secondResult.payload as ResultFormat.FullResponse,
        false
)

val results = listOf(firstResult, secondResult, thirdResult)
fun randomResult() = results[Math.floor(Math.random() * results.size).toInt()]

val allResults = Array(50) { randomResult() }.toList()

