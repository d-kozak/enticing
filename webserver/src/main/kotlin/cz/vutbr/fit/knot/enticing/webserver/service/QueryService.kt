package cz.vutbr.fit.knot.enticing.webserver.service

import cz.vutbr.fit.knot.enticing.webserver.service.mock.documentText
import org.springframework.stereotype.Service

@Service
class QueryService {

    fun query(query: String, selectedSettings: Long) = ""


    fun context(documentId: Long, location: Long, size: Long) = ""


    fun document(documentId: Long) = documentText
}