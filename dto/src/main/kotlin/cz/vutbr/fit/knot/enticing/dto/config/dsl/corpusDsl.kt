package cz.vutbr.fit.knot.enticing.dto.config.dsl

data class
CorpusConfiguration(
        val corpusName: String,
        var indexes: Map<String, Index> = emptyMap(),
        var entities: Map<String, Entity> = emptyMap()
) {
    fun indexes(block: IndexConfigDsl.() -> Unit = {}) = indexesDslInternal(block)
            .also { this.indexes = it }

    fun entities(block: EntityConfigDsl.() -> Unit = {}) = entitiesDslInternal(block)
            .also { this.entities = it }

    fun isEntityAttribute(name: String): Boolean = name in entityAttributes

    val entityAttributes: Set<String>
        get() = entities.values.flatMap { it.attributes.keys }.toSet()

    fun indexOf(name: String) = indexes[name]?.columnIndex ?: throw IllegalArgumentException("Unknown index $name")

}

internal fun corpusDslInternal(name: String, block: CorpusConfiguration.() -> Unit = {}): CorpusConfiguration = CorpusConfiguration(name).apply(block)
