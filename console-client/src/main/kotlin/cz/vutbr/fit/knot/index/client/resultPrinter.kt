package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup

fun printResult(searchResult: IndexServer.CollectionSearchResult) {
    for (match in searchResult.matched) {
        println("doc ${match.documentTitle}")
        println("text ${match.payload}")
    }
}

@Cleanup("Unnecessary redundant")
fun printResult(searchResult: IndexServer.SearchResult) {
    for (match in searchResult.matched) {
        println("doc ${match.documentTitle}")
        println("text ${match.payload}")
    }
}