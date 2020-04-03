package cz.vutbr.fit.knot.enticing.testing.util

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.format.result.ResultFormat

fun assertUniqueResults(results: List<IndexServer.SearchResult>) {
    val ids = mutableSetOf<String>()
    val duplicities = mutableListOf<String>()
    for (result in results) {
        val snippet = result.payload as ResultFormat.Snippet
        val id = "${result.collection}-${result.documentId}-${snippet.location}-${snippet.size}"
        if (!ids.add(id)) {
            duplicities.add(id)
            System.err.println("Id '$id' is already present, possible duplicity")
        }
    }
    System.err.println("Ids $duplicities not unique")
}