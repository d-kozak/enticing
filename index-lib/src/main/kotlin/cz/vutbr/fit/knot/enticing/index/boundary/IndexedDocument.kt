package cz.vutbr.fit.knot.enticing.index.boundary

import cz.vutbr.fit.knot.enticing.dto.annotation.Speed

/**
 * Retrieved document from the indexed data
 */
interface IndexedDocument {
    val id: Int
    val uuid: String
    val title: String
    val uri: String
    val size: Int
    @Speed("If necessary, char[] or MutableStrings or some else more low level abstraction can be used here")
    val content: List<String>
}