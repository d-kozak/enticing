package cz.vutbr.fit.knot.enticing.index.client

import cz.vutbr.fit.knot.enticing.dto.IndexServer
import cz.vutbr.fit.knot.enticing.dto.annotation.Cleanup

fun printResult(resultList: IndexServer.CollectionResultList) {
    for (match in resultList.searchResults) {
        println("doc ${match.documentTitle}")
        println("text ${match.payload}")
    }
}

@Cleanup("Unnecessary redundant")
fun printResult(searchResult: IndexServer.IndexResultList) {
    for (match in searchResult.searchResults) {
        println("doc ${match.documentTitle}")
        println("text ${match.payload}")
    }
}