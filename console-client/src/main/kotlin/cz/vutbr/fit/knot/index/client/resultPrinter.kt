package cz.vutbr.fit.knot.index.client

import cz.vutbr.fit.knot.enticing.dto.response.SearchResult

fun printResult(searchResult: SearchResult) {
    for (match in searchResult.matched) {
        println("doc ${match.documentTitle}")
        println("text ${match.payload}")
    }
}