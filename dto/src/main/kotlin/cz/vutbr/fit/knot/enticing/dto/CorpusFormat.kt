package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.AttributeConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.EntityConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.IndexConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata.MetadataConfiguration
import javax.validation.constraints.NotBlank


typealias IndexName = String
typealias Description = String
typealias EntityName = String
typealias Attribute = String

/**
 * Format of the data available on an IndexServer
 */
data class CorpusFormat(
        /**
         * Name of the corpus this server handles
         */
        @field:NotBlank
        var corpusName: String,
        /**
         * Indexes with their descriptions
         */
        val indexes: Map<IndexName, Description>,

        /**
         * Entities with their attributes
         */
        val entities: Map<EntityName, EntityFormat>
) {
    /**
     * Just for testing
     */
    internal constructor(name: String, indexes: List<String>, entities: Map<String, List<String>>)
            : this(name,
            indexes.associateWith { it },
            entities.mapValues { EntityFormat(it.key, it.value.associateWith { AttributeInfo(it, it) }) }
    )
}

fun CorpusFormat.toMetadataConfiguration(): MetadataConfiguration {
    val indexMetadata = LinkedHashMap<String, IndexConfiguration>()
    val entityMetadata = LinkedHashMap<String, EntityConfiguration>()

    for ((name, description) in indexes) {
        indexMetadata[name] = IndexConfiguration(name, description)
    }

    for ((name, format) in entities) {
        entityMetadata[name] = EntityConfiguration(
                name,
                format.description,
                format.attributes.mapValues { (attributeName, attributeInfo) ->
                    AttributeConfiguration(attributeName, attributeInfo.description)
                            .also {
                                it.index = indexMetadata[attributeInfo.correspondingIndex]
                                        ?: throw IllegalArgumentException("cannot find index ${attributeInfo.correspondingIndex}")
                            }
                }.toMutableMap()
        )
    }

    return MetadataConfiguration(indexMetadata, entityMetadata)
}

/**
 * Format of one attribute
 *
 * Basically kotlin's pair, only created to allow for custom names instead of first na second
 */
data class EntityFormat(
        val description: Description,
        val attributes: Map<Attribute, AttributeInfo>
)

data class AttributeInfo(
        val correspondingIndex: String,
        val description: Description
)

/**
 * Convert corpus configuration to Corpusformat
 */
fun MetadataConfiguration.toCorpusFormat() =
        CorpusFormat(
                "unknown",
                indexes.mapValues { (_, index) -> index.description },
                entities.mapValues { (_, entity) ->
                    EntityFormat(entity.description,
                            entity.attributes.mapValues { (_, attribute) -> AttributeInfo(attribute.index.name, attribute.description) })
                }
        )

fun mergeCorpusFormats(vararg formats: CorpusFormat): CorpusFormat = mergeCorpusFormats(formats.toList())

fun mergeCorpusFormats(formats: List<CorpusFormat>): CorpusFormat {
    require(formats.isNotEmpty()) { "At least one corpus format is necessary" }
    val start = formats[0]
    val rest = formats.subList(1, formats.size)

    val newIndexes = mutableMapOf<String, Description>()
    for ((index, description) in start.indexes) {
        if (rest.all { it.indexes.containsKey(index) })
            newIndexes[index] = description
    }
    val newEntities = mutableMapOf<String, EntityFormat>()
    for ((entityName, entityFormat) in start.entities) {
        if (rest.all { it.entities.containsKey(entityName) }) {
            val attributes = mutableMapOf<String, AttributeInfo>()
            for ((attribute, attributeInfo) in entityFormat.attributes) {
                if (rest.all { it.entities[entityName]!!.attributes.containsKey(attribute) }) {
                    attributes[attribute] = attributeInfo
                }
            }
            newEntities[entityName] = EntityFormat(entityFormat.description, attributes)
        }
    }
    val newName = formats.map { it.corpusName }.distinct().joinToString("-")
    return CorpusFormat(newName, newIndexes, newEntities)
}