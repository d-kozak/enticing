package cz.vutbr.fit.knot.enticing.index.server.utils

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat

internal val templateSearchQuery = SearchQuery(
        "foo bar baz",
        20,
        mapOf("one" to Offset(0, 0)),
        TextMetadata.Predefined("none"),
        ResultFormat.FULL,
        TextFormat.STRING_WITH_METADATA
)

internal val searchDummyResult = IndexServer.IndexResultList(
        listOf(IndexServer.SearchResult(
                "col",
                10,
                10,
                5,
                "google.com",
                "title",
                ResultFormat.Snippet.Html("texty text"),
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
        ResultFormat.Snippet.Html("how to use google for dummies")
)


internal val templateContextExtensionQuery = IndexServer.ContextExtensionQuery(
        collection = "col1",
        docId = 1,
        location = 10,
        size = 14,
        extension = 20
)

internal val contextExtensionDummyResult = SnippetExtension(
        ResultFormat.Snippet.Html("null"),
        ResultFormat.Snippet.Html("null"),
        false)
