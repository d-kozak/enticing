package cz.vutbr.fit.knot.enticing.index.config.dsl

var indexes: IndexConfigDsl = mutableMapOf()

data class Index(
        var name: String,
        var description: String = "",
        var type: FieldType = FieldType.Text,
        var columnIndex: Int = 0
) {
    infix fun whichIs(description: String) {
        this.description = description
    }
}

typealias IndexConfigDsl = MutableMap<String, Index>

fun IndexConfigDsl.index(name: String, block: Index.() -> Unit = {}) = Index(name).apply(block)
        .also { it.columnIndex = this.size }
        .also { this[name] = it }

infix
fun String.whichIs(description: String): Index = Index(this, description)
        .also { it.columnIndex = indexes.size }
        .also { indexes[this] = it }

internal fun indexesDslInternal(block: IndexConfigDsl.() -> Unit): IndexConfigDsl = mutableMapOf<String, Index>()
        .also { indexes = it }
        .apply(block)
        .also { indexes = mutableMapOf() }


