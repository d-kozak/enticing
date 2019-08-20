package cz.vutbr.fit.knot.enticing.index.utils

import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument

internal fun testDocument(vararg content: String) = object : IndexedDocument {
    override val id: Int = 42
    override val uuid: String = "foo"
    override val title: String = "bar"
    override val uri: String = "baz"
    override val size: Int = 42
    override val content: List<String> = content.toList()
}