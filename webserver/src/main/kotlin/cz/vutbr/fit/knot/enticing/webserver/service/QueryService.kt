package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.dto.AnnotatedText
import cz.vutbr.fit.knot.enticing.dto.Payload
import cz.vutbr.fit.knot.enticing.dto.Webserver
import cz.vutbr.fit.knot.enticing.dto.query.ContextExtensionQuery
import cz.vutbr.fit.knot.enticing.webserver.service.mock.allResults
import cz.vutbr.fit.knot.enticing.webserver.service.mock.dummyDocument
import cz.vutbr.fit.knot.enticing.webserver.service.mock.loremOneSentence
import org.springframework.stereotype.Service

@Service
class QueryService {

    fun query(query: String, selectedSettings: Long): List<Webserver.Snippet> =
            if ("nertag:person (visited|entered)" == query) allResults else emptyList()


    fun context(query: ContextExtensionQuery): Webserver.Snippet = Webserver.Snippet(query.host, query.collection, 42, query.location.toInt(), query.size.toInt() + 10, "http://www.google.com", "title", Payload.FullResponse.Annotated(AnnotatedText(loremOneSentence, emptyMap(), emptyList(), emptyList())), Math.random() > 0.4)

    fun document(query: Webserver.DocumentQuery): Webserver.FullDocument = dummyDocument
}