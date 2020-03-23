package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat


internal data class FailOnPurposeException(val msg: String) : Exception()


internal val templateQuery = SearchQuery(
        "foo bar baz",
        20,
        emptyMap(),
        TextMetadata.Predefined("none"),
        cz.vutbr.fit.knot.enticing.dto.ResultFormat.SNIPPET,
        TextFormat.STRING_WITH_METADATA
)

internal val googleFirstResult = IndexServer.IndexResultList(
        listOf(IndexServer.SearchResult(
                "col",
                10,
                "c1be4c68-2e2a-4823-a8e3-0b52fc0e6a19",
                "google.com",
                "title",
                ResultFormat.Snippet.Html("texty text", 10, 5, false)
        )),
        mapOf("one" to Offset(42, 84))
)