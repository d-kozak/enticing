package cz.vutbr.fit.knot.enticing.index.mg4j

/**
 * Meta marks from mg4j documents
 */
enum class DocumentMarks(val mark: String) {
    DOC("%%#DOC"),
    PAGE("%%#PAGE"),
    PAR("%%#PAR"),
    SENT("%%#SEN"),
    META("%%#");

    val bytes = mark.toByteArray()
}

fun String.isDoc() = this.startsWith(DocumentMarks.DOC.mark)
fun String.isPage() = this.startsWith(DocumentMarks.PAGE.mark)
fun String.isPar() = this.startsWith(DocumentMarks.PAR.mark)
fun String.isSent() = this.startsWith(DocumentMarks.SENT.mark)
fun String.isMetaInfo() = this.startsWith(DocumentMarks.META.mark)

/**
 * Signals that there should be no space between current token and the previous one
 */
internal const val glueSymbol1 = "|G__"
internal const val glueSymbol2 = "|GGG"

fun String.isGlued() = this.endsWith(glueSymbol1) || endsWith(glueSymbol2)
fun String.removeGlue(): String = this.substring(0, this.lastIndexOf("|"))

fun ByteArray.isDoc() = this.startsWith(DocumentMarks.DOC.bytes)
fun ByteArray.isPage() = this.startsWith(DocumentMarks.PAGE.bytes)
fun ByteArray.isMetaInfo() = this.startsWith(DocumentMarks.META.bytes)

fun ByteArray.startsWith(prefix: ByteArray): Boolean {
    for (i in 0 until prefix.size) {
        if (this[i] != prefix[i]) return false
    }
    return true
}