package cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfigurationUnit
import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationVisitor

fun metadataConfiguration(block: MetadataConfiguration.() -> Unit) = MetadataConfiguration().apply(block)

/**
 * Configuration of metadata
 * usable both for individual index servers or whole corpuses
 */
data class MetadataConfiguration(
        var indexes: LinkedHashMap<String, IndexConfiguration> = LinkedHashMap(),
        var entities: LinkedHashMap<String, EntityConfiguration> = LinkedHashMap(),
        /**
         * The index based on which the type of entity is determined
         */
        var entityIndexName: String = "nertag",

        /**
         * The index based on which the length of entity is determined(how many words it consists of)
         */
        var lengthIndexName: String = "nerlength"
) : EnticingConfigurationUnit {

    val entityIndex: IndexConfiguration?
        get() = indexes[entityIndexName]

    val lengthIndex: IndexConfiguration?
        get() = indexes[lengthIndexName]

    val glueIndex: IndexConfiguration?
        get() = indexes["_glue"]

    /**
     * Indexes of mg4j indexes that are used for entity attributes, left inclusive, right exclusive
     *
     * currently those are url to param9
     */
    internal var attributeIndexes: IntRange = IntRange.EMPTY

    val firstAttributeIndex: Int?
        get() = if (attributeIndexes.isEmpty()) null else attributeIndexes.first

    val attributeLimit: Int
        get() = attributeIndexes.last - attributeIndexes.first + 1

    fun indexes(block: IndexList.() -> Unit) {
        val indexList = IndexList(this).apply(block)
        for (indexConfiguration in indexList.indexes) {
            indexes[indexConfiguration.name] = indexConfiguration
        }
    }

    fun entities(block: EntityList.() -> Unit) {
        val entityList = EntityList(this).apply(block)
        for (entityConfiguration in entityList.entities) {
            entities[entityConfiguration.name] = entityConfiguration
        }
    }

    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitMetadataConfiguration(this)
    }

    fun indexOf(name: String): Int = indexes.getValue(name).columnIndex
}

