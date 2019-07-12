package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.dto.IndexServer

fun printResult(searchResult: IndexServer.SearchResult) {
    for (match in searchResult.matched) {
        println("doc ${match.documentTitle}")
        println("text ${match.payload}")
    }
}