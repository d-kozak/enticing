package cz.vutbr.fit.knot.enticing.index.server.utils

import cz.vutbr.fit.knot.enticing.dto.*

internal val templateSearchQuery = SearchQuery(
        "foo bar baz",
        20,
        mapOf("one" to Offset(0, 0)),
        TextMetadata.Predefined("none"),
        ResponseType.FULL,
        ResponseFormat.ANNOTATED_TEXT
)

internal val searchDummyResult = IndexServer.SearchResult(
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
        mapOf("one" to Offset(42, 84))
)

internal val templateDocumentQuery = IndexServer.DocumentQuery(
        collection = "col1",
        documentId = 1
)

internal val documentDummyResult = IndexServer.FullDocument(
        "how to use google",
        "google.com/howto",
        Payload.FullResponse.Html("how to use google for dummies")
)


internal val templateContextExtensionQuery = IndexServer.ContextExtensionQuery(
        collection = "col1",
        docId = 1,
        location = 10,
        size = 14,
        extension = 20
)

internal val contextExtensionDummyResult = SnippetExtension(
        Payload.FullResponse.Html("null"),
        Payload.FullResponse.Html("null"),
        false)
