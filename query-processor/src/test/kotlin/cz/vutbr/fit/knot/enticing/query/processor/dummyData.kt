package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.Payload


internal data class FailOnPurposeException(val msg: String) : Exception()


internal val templateQuery = SearchQuery(
        "foo bar baz",
        20,
        Offset(0, 0),
        TextMetadata.Predefined("none"),
        ResponseType.FULL,
        ResponseFormat.ANNOTATED_TEXT
)

internal val googleFirstResult = IndexServer.SearchResult(
        listOf(IndexServer.Snippet(
                "col",
                10,
                10,
                5,
                "google.com",
                "title",
                Payload.FullResponse.Html("texty text"),
                false
        )),
        Offset(42, 84)
)