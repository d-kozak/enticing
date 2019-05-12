package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.webserver.dto.AnnotatedText
import cz.vutbr.fit.knot.enticing.webserver.dto.IndexedDocument
import cz.vutbr.fit.knot.enticing.webserver.dto.SearchResult
import cz.vutbr.fit.knot.enticing.webserver.service.mock.allResults
import cz.vutbr.fit.knot.enticing.webserver.service.mock.dummyDocument
import cz.vutbr.fit.knot.enticing.webserver.service.mock.loremOneSentence
import org.springframework.stereotype.Service

@Service
class QueryService {

    fun query(query: String, selectedSettings: Long): List<SearchResult> =
            if ("nertag:person (visited|entered)" == query) allResults else emptyList()


    fun context(documentId: Long, location: Long, size: Long): SearchResult = SearchResult(documentId, location, size + 10, AnnotatedText(loremOneSentence, emptyMap(), emptyList()), "foo", Math.random() > 0.4)

    fun document(documentId: Long): IndexedDocument = dummyDocument
}