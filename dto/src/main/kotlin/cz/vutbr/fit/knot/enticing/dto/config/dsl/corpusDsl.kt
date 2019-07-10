package cz.vutbr.fit.knot.enticing.dto.config.dsl

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

    fun getMetaIndexes(defaultIndex: String): Set<String> = indexes.keys.toMutableSet().also { it.remove(defaultIndex) }

    fun indexOf(name: String) = indexes[name]?.columnIndex ?: throw IllegalArgumentException("Unknown index $name")

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
     * Indexes of mg4j indexes that are used for entity attributes, both inclusive
     *
     * currently those are url to param9
     */
    lateinit var attributeIndexes: Pair<Int, Int>

    /**
     * Indexes that should be added as attributes to all entities.
     *
     * This is done in the validation step after ensuring they exist.
     * Currently this is just nertype index
     * LinkedHashSet is used to preserve the ordering
     */
    var extraAttributes: LinkedHashSet<String> = LinkedHashSet()

    fun extraAttributes(vararg names: String) {
        this.extraAttributes = LinkedHashSet(names.toList())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EntityMapping) return false

        if (entityIndex != other.entityIndex) return false
        if (attributeIndexes != other.attributeIndexes) return false
        if (extraAttributes != other.extraAttributes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = entityIndex.hashCode()
        result = 31 * result + attributeIndexes.hashCode()
        result = 31 * result + extraAttributes.hashCode()
        return result
    }

    override fun toString(): String {
        return "EntityMapping(entityIndex='$entityIndex', attributeIndexes=$attributeIndexes, extraAttributes=$extraAttributes)"
    }

}


fun CorpusConfiguration.validate() = mutableListOf<String>().also { this.validate(it) }

/**
 * Validates corpus configuration
 *
 * During the process, the corresponding index for each attribute of each entity is found and saved
 * @param errors list to which the error messages should be appended
 */
fun CorpusConfiguration.validate(errors: MutableList<String>) {
    // todo @cleanUp refacotyr

    if (corpusName.isBlank()) {
        errors.add("Corpus name should neither be empty nor blank")
    }

    for ((key, index) in indexes) {
        checkName(key, index.name, errors)
    }
    val indexList = indexes.values.toList()
    val from = entityMapping.attributeIndexes.first
    if (from >= 0) {
        for ((key, entity) in entities) {
            checkName(key, entity.name, errors)

            for (attribute in entity.attributes.values) {
                val i = from + attribute.columnIndex
                if (i < 0 || i >= indexList.size) {
                    errors.add("Index ${attribute.columnIndex} for attribute ${attribute.name} in entity ${entity.name} is exceeding the number of indexes ${indexList.size}")
                } else {
                    attribute.correspondingIndex = indexList[i].name
                }
            }
        }
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

    val maxAttributeSize = entityMapping.attributeIndexes.second - entityMapping.attributeIndexes.first + 1
    errors.addAll(
            entities.values
                    .filter { it.attributes.size > maxAttributeSize }
                    .map {
                        val attributes = it.attributes.values.toList()
                        "entity ${it.name} has too many attributes, ${attributes.subList(maxAttributeSize, attributes.size).map { it.name }} are above the specified size $maxAttributeSize(range ${entityMapping.attributeIndexes.first}..${entityMapping.attributeIndexes.second})"
                    }
    )

    for (indexName in entityMapping.extraAttributes) {
        val index = indexes[indexName]
        if (index != null) {
            for (entity in entities.values) {
                if (entity.attributes[indexName] == null) {
                    entity.attributes[indexName] = Attribute(index.name, entity.attributes.size, index.description, index.type, index.name)
                } else {
                    errors.add("Attribute with name $indexName already exists in entity ${entity.name}")
                }
            }
        } else {
            errors.add("extra attribute $indexName was not found within indexes")
        }
    }

}

private fun checkName(key: String, name: String, errors: MutableList<String>) {
    if (key.isBlank()) {
        errors.add("Name should not be blank")
    }
    if (name.isBlank()) {
        errors.add("Name should not be blank")
    }
    if (key != name) {
        errors.add("Inconsistency in the name of entity, key was $key, name was $name")
    }
}
