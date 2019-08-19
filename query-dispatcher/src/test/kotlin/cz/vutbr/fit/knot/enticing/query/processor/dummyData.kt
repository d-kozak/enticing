package cz.vutbr.fit.knot.enticing.query.processor

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat


internal data class FailOnPurposeException(val msg: String) : Exception()


internal val templateQuery = SearchQuery(
        "foo bar baz",
        20,
        emptyMap(),
        TextMetadata.Predefined("none"),
        ResponseType.FULL,
        ResponseFormat.ANNOTATED_TEXT
)

internal val googleFirstResult = IndexServer.IndexResultList(
        listOf(IndexServer.Snippet(
                "col",
                10,
                10,
                5,
                "google.com",
                "title",
                ResultFormat.FullResponse.Html("texty text"),
                false
        )),
        mapOf("one" to Offset(42, 84))
)