package cz.vutbr.fit.knot.enticing.eql.compiler

import java.io.File

private class Dummy

internal fun loadFile(fileName: String): File = File(Dummy::class.java.classLoader.getResource(fileName)?.file
        ?: throw IllegalArgumentException("File with name $fileName not found in the resources folder"))

internal fun forEachQuery(fileName: String, block: (String) -> Boolean) {
    val failed = mutableSetOf<String>()
    for (query in loadFile(fileName).readLines()) {
        if (query.isEmpty() || query.startsWith("#")) continue
        println("Processing query '$query'")
        if (!block(query)) {
            failed.add(query)
        }
    }
    if (failed.isNotEmpty()) {
        System.err.println("Following queries were not handled as expected")
        failed.forEach(System.err::println)
        throw AssertionError(failed.toString())
    }
}