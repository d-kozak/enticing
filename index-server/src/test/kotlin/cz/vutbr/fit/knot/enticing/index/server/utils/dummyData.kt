package cz.vutbr.fit.knot.enticing.index.server.utils

import cz.vutbr.fit.knot.enticing.dto.*
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat

internal val templateSearchQuery = SearchQuery(
        "foo bar baz",
        20,
        mapOf("one" to Offset(0, 0)),
        TextMetadata.Predefined("none"),
        cz.vutbr.fit.knot.enticing.dto.ResultFormat.SNIPPET,
        TextFormat.STRING_WITH_METADATA
)

internal val searchDummyResult = IndexServer.IndexResultList(
        listOf(IndexServer.SearchResult(
                "col",
                10,
                "c1be4c68-2e2a-4823-a8e3-0b52fc0e6a19",
                "google.com",
                "title",
                ResultFormat.Snippet.Html("texty text", 10, 0, false)
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
        ResultFormat.Snippet.Html("how to use google for dummies", 0, 0, false)
)


internal val templateContextExtensionQuery = IndexServer.ContextExtensionQuery(
        collection = "col1",
        docId = 1,
        location = 10,
        size = 14,
        extension = 20
)

internal val contextExtensionDummyResult = SnippetExtension(
        ResultFormat.Snippet.Html("null", 0, 0, false),
        ResultFormat.Snippet.Html("null", 0, 0, false),
        false)
