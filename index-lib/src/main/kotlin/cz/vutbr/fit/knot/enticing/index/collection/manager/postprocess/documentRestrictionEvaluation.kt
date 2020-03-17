package cz.vutbr.fit.knot.enticing.index.collection.manager.postprocess

import cz.vutbr.fit.knot.enticing.eql.compiler.ast.DocumentRestriction
import cz.vutbr.fit.knot.enticing.index.boundary.IndexedDocument

fun DocumentRestriction?.evaluate(document: IndexedDocument): Boolean = when (this) {
    is DocumentRestriction.Id -> this.text == document.uuid
    is DocumentRestriction.Title -> this.text == document.title
    is DocumentRestriction.Url -> this.text == document.uri
    null -> true
}