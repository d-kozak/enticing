package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.query.ContextExtensionQuery
import cz.vutbr.fit.knot.enticing.dto.query.DocumentQuery
import cz.vutbr.fit.knot.enticing.dto.response.AnnotatedText
import cz.vutbr.fit.knot.enticing.dto.response.IndexedDocument
import cz.vutbr.fit.knot.enticing.dto.response.Match
import cz.vutbr.fit.knot.enticing.dto.response.Payload
import cz.vutbr.fit.knot.enticing.webserver.service.mock.allResults
import cz.vutbr.fit.knot.enticing.webserver.service.mock.dummyDocument
import cz.vutbr.fit.knot.enticing.webserver.service.mock.loremOneSentence
import org.springframework.stereotype.Service

@Service
class QueryService {

    fun query(query: String, selectedSettings: Long): List<Match> =
            if ("nertag:person (visited|entered)" == query) allResults else emptyList()


    fun context(query: ContextExtensionQuery): Match = Match(query.collection, 42, query.location, query.size + 10, "http://www.google.com", Payload.Snippet.Json(AnnotatedText(loremOneSentence, emptyMap(), emptyList(), emptyList())), Math.random() > 0.4)

    fun document(query: DocumentQuery): IndexedDocument = dummyDocument
}