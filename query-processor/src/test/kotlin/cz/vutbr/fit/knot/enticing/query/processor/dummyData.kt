package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.query.*
import cz.vutbr.fit.knot.enticing.dto.response.Match
import cz.vutbr.fit.knot.enticing.dto.response.Payload
import cz.vutbr.fit.knot.enticing.dto.response.SearchResult

internal data class FailOnPurposeException(val msg: String) : Exception()


internal val templateQuery = SearchQuery(
        "foo bar baz",
        20,
        Offset(0, 0),
        TextMetadata.Predefined("none"),
        ResponseType.SNIPPET,
        ResponseFormat.JSON
)

internal val googleFirstResult = SearchResult(
        listOf(Match(
                "col",
                10,
                10,
                5,
                "google.com",
                "title",
                Payload.Snippet.Html("texty text"),
                false
        )),
        Offset(42, 84)
)