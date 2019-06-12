package cz.vutbr.fit.knot.enticing.index.config.dsl

typealias Attribute = Index
typealias AttributesDsl = MutableMap<String, Attribute>

data class Entity(
        var name: String,
        var description: String = "",
        var attributes: AttributesDsl = mutableMapOf()
) {
    fun attributes(block: AttributesDsl.() -> Unit) = mutableMapOf<String, Attribute>()
            .apply(block)
            .also { this.attributes = it }
}


fun AttributesDsl.attribute(name: String, block: Attribute.() -> Unit = {}) = Index(name).apply(block)
        .also { it.columnIndex = this.size }
        .also { this[name] = it }

typealias EntityConfigDsl = MutableMap<String, Entity>

fun EntityConfigDsl.entity(name: String, block: Entity.() -> Unit = {}) = Entity(name).apply(block)
        .also { this[name] = it }

internal fun entitiesDslInternal(block: EntityConfigDsl.() -> Unit) = mutableMapOf<String, Entity>()
        .also(block)
