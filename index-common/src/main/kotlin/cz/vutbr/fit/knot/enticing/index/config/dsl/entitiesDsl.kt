package cz.vutbr.fit.knot.enticing.index.config.dsl

typealias Attribute = Index
typealias AttributesDsl = MutableMap<String, Attribute>

data class Entity(
        var name: String,
        var description: String = "",
        var attributes: AttributesDsl = mutableMapOf()
) {
    fun attributes(vararg names: String, block: AttributesDsl.() -> Unit = {}): AttributesDsl {
        val predefined = names.asSequence()
                .mapIndexed { i, name -> name to Index(name, columnIndex = attributes.size + i) }
                .toMap()
                .toMutableMap()
        return predefined.apply(block)
                .also { this.attributes = it }
    }
}

private var entities = mutableMapOf<String, Entity>()

infix fun String.with(attributes: Array<String>): Entity {
    val entity = Entity(this)
    entity.attributes(*attributes)
    entities[this] = entity
    return entity
}

fun attributes(vararg names: String): Array<String> = Array(names.size) { names[it] }

fun AttributesDsl.attribute(name: String, block: Attribute.() -> Unit = {}) = Index(name).apply(block)
        .also { it.columnIndex = this.size }
        .also { this[name] = it }

typealias EntityConfigDsl = MutableMap<String, Entity>

fun EntityConfigDsl.entity(name: String, block: Entity.() -> Unit = {}) = Entity(name).apply(block)
        .also { this[name] = it }

internal fun entitiesDslInternal(block: EntityConfigDsl.() -> Unit) = mutableMapOf<String, Entity>()
        .also { entities = it }
        .also(block)
        .also { entities = mutableMapOf() }
