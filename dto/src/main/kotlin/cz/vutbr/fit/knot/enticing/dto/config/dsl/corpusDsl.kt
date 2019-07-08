package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.query.Entities
import cz.vutbr.fit.knot.enticing.dto.query.Indexes
import cz.vutbr.fit.knot.enticing.dto.query.TextMetadata

fun corpusConfig(name: String, block: CorpusConfiguration.() -> Unit = {}): CorpusConfiguration = CorpusConfiguration(name).apply(block)

data class CorpusConfiguration(
        val corpusName: String,
        var indexes: Map<String, Index> = emptyMap(),
        var entities: Map<String, Entity> = emptyMap(),
        val entityMapping: EntityMapping = EntityMapping()
) {
    fun indexes(block: IndexConfigDsl.() -> Unit = {}) = indexesDslInternal(block)
            .also { this.indexes = it }

    fun entities(block: EntityConfigDsl.() -> Unit = {}) = entitiesDslInternal(block)
            .also { this.entities = it }

    fun entityMapping(block: EntityMapping.() -> Unit) = entityMapping.apply(block)

    fun isEntityAttribute(name: String): Boolean = name in entityAttributes

    val entityAttributes: Set<String>
        get() = entities.values.flatMap { it.attributes.keys }.toSet()

    fun indexOf(name: String) = indexes[name]?.columnIndex ?: throw IllegalArgumentException("Unknown index $name")

    fun filterBy(metadata: TextMetadata, defaultIndex: String): CorpusConfiguration = when (metadata) {
        is TextMetadata.Predefined -> {
            when (metadata.value) {
                "all" -> this
                "none" -> this.copy(entities = emptyMap(), indexes = onlySpecifiedIndexes(defaultIndex))
                else -> throw IllegalArgumentException("Unknown toplevel format ${metadata.value}")
            }
        }

        is TextMetadata.ExactDefinition -> {
            val indexes = when (metadata.indexes) {
                is Indexes.Predefined -> {
                    when (metadata.indexes.value) {
                        "all" -> this.indexes
                        "none" -> onlySpecifiedIndexes(defaultIndex)
                        else -> throw IllegalArgumentException("Unknown index format ${metadata.indexes.value}")
                    }
                }
                is Indexes.ExactDefinition ->
                    if (defaultIndex in metadata.indexes.names)
                        onlySpecifiedIndexes(metadata.indexes.names)
                    else {
                        val names = metadata.indexes.names.toMutableList()
                        names.add(defaultIndex)
                        onlySpecifiedIndexes(names)
                    }

            }

            val entities = when (metadata.entities) {
                is Entities.Predefined -> {
                    when (metadata.entities.value) {
                        "all" -> this.entities
                        "none" -> emptyMap()
                        else -> throw IllegalArgumentException("Unknown entity format ${metadata.entities.value}")
                    }
                }
                is Entities.ExactDefinition -> {
                    this.entities
                }
            }

            this.copy(indexes = indexes, entities = entities)
        }
    }

    private fun onlySpecifiedIndexes(indexes: List<String>): Map<String, Index> = indexes.map {
        it to (this.indexes[it] ?: throw IllegalArgumentException("Could not find index $it"))
    }.toMap()

    private fun onlySpecifiedIndexes(vararg indexes: String) = onlySpecifiedIndexes(indexes.toList())
}

/**
 * Describes how entity attributes are mapped to indexes
 */
class EntityMapping {
    /**
     * The index based on which the type of entity is determined
     */
    lateinit var entityIndex: String

    /**
     * Indexes of mg4j indexes that are used for entity attributes
     *
     * currently those are url to param9
     */
    lateinit var attributeIndexes: Pair<Int, Int>

    /**
     * Indexes that do not belong to attributeIndexes, but should still be accessible through entities
     *
     * currently those are nertype and nerlength
     */
    lateinit var extraEntityIndexes: Set<String>

    fun extraIndexes(vararg names: String) {
        this.extraEntityIndexes = names.toSet()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EntityMapping) return false

        if (entityIndex != other.entityIndex) return false
        if (attributeIndexes != other.attributeIndexes) return false
        if (extraEntityIndexes != other.extraEntityIndexes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = entityIndex.hashCode()
        result = 31 * result + attributeIndexes.hashCode()
        result = 31 * result + extraEntityIndexes.hashCode()
        return result
    }

    override fun toString(): String {
        return "EntityMapping(entityIndex='$entityIndex', attributeIndexes=$attributeIndexes, extraEntityIndexes=$extraEntityIndexes)"
    }

}

/**
 * Validates corpus configuration
 *
 * @return list of error messages
 */
fun CorpusConfiguration.validate(): List<String> {
    val errors = mutableListOf<String>()

    if (corpusName.isBlank()) {
        errors.add("Corpus name should neither be empty nor blank")
    }

    if (indexes[entityMapping.entityIndex] == null) {
        errors.add("entity index ${entityMapping.entityIndex} not found")
    }

    val indexRange = 0 until indexes.size
    if (entityMapping.attributeIndexes.first !in indexRange) {
        errors.add("attribute index lower bound ${entityMapping.attributeIndexes.first} is not within $indexRange")
    }
    if (entityMapping.attributeIndexes.second !in indexRange) {
        errors.add("attribute index upper bound ${entityMapping.attributeIndexes.second} is not within $indexRange")
    }

    errors.addAll(
            entityMapping.extraEntityIndexes
                    .filter { indexes[it] == null }
                    .map { "extra index $it was not found within indexes" }
    )


    val maxAttributeSize = entityMapping.attributeIndexes.second - entityMapping.attributeIndexes.first + 1
    errors.addAll(
            entities.values
                    .filter { it.attributes.size > maxAttributeSize }
                    .map {
                        val attributes = it.attributes.values.toList()
                        "entity ${it.name} has too many attributes, ${attributes.subList(maxAttributeSize, attributes.size).map { it.name }} are above the specified size $maxAttributeSize(range ${entityMapping.attributeIndexes.first}..${entityMapping.attributeIndexes.second})"
                    }
    )

    return errors
}


