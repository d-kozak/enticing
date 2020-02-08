package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata

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
        var entityIndex: String = "nertag",

        /**
         * The index based on which the length of entity is determined(how many words it consists of)
         */
        var lengthIndex: String = "nerlength"
) {

    /**
     * Indexes of mg4j indexes that are used for entity attributes, left inclusive, right exclusive TODO change client code appropriately
     *
     * currently those are url to param9
     */
    internal var attributeIndexes: IntRange = IntRange.EMPTY

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
}

