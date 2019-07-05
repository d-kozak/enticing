package cz.vutbr.fit.knot.enticing.index.server.utils

import cz.vutbr.fit.knot.enticing.dto.query.*
import cz.vutbr.fit.knot.enticing.dto.response.Match
import cz.vutbr.fit.knot.enticing.dto.response.Payload
import cz.vutbr.fit.knot.enticing.dto.response.SearchResult
import cz.vutbr.fit.knot.enticing.dto.utils.MResult

internal val templateQuery = SearchQuery(
        "foo bar baz",
        20,
        Offset(0, 0),
        TextMetadata.Predefined("none"),
        ResponseType.SNIPPET,
        ResponseFormat.JSON
)

internal val dummyResult = MResult.success(SearchResult(
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
        Offset(42, 84))
)
