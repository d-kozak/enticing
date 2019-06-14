package cz.vutbr.fit.knot.enticing.indexer.mg4j

enum class DocumentMarks(val mark: String) {
    DOC("%%#DOC"),
    PAGE("%%#PAGE");

    val bytes = mark.toByteArray()
}

fun ByteArray.isDoc() = this.startsWith(DocumentMarks.DOC.bytes)
fun ByteArray.isPage() = this.startsWith(DocumentMarks.PAGE.bytes)

fun ByteArray.startsWith(prefix: ByteArray): Boolean {
    for (i in 0 until prefix.size) {
        if (this[i] != prefix[i]) return false
    }
    return true
}