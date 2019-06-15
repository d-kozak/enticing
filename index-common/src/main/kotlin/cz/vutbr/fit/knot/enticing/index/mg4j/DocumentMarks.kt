package cz.vutbr.fit.knot.enticing.index.mg4j

enum class DocumentMarks(val mark: String) {
    DOC("%%#DOC"),
    PAGE("%%#PAGE");

    val bytes = mark.toByteArray()
}

val metaPrefix = "%%#".toByteArray()

fun ByteArray.isDoc() = this.startsWith(DocumentMarks.DOC.bytes)
fun ByteArray.isPage() = this.startsWith(DocumentMarks.PAGE.bytes)
fun ByteArray.isMetaInfo(): Boolean = this.startsWith(metaPrefix)

fun ByteArray.startsWith(prefix: ByteArray): Boolean {
    for (i in 0 until prefix.size) {
        if (this[i] != prefix[i]) return false
    }
    return true
}