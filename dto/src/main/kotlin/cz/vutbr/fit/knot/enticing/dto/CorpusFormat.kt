package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.Entity
import cz.vutbr.fit.knot.enticing.dto.config.dsl.Index
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
        val corpusName: String,
        /**
         * Indexes with their descriptions
         */
        val indexes: Map<IndexName, Description>,

        /**
         * Entities with their attributes
         */
        val entities: Map<EntityName, EntityFormat>
) {
    internal constructor(name: String, indexes: List<String>, entities: Map<String, List<String>>)
            : this(name, indexes.map { it to "" }.toMap(), entities.mapValues { EntityFormat("", it.value.map { it to "" }.toMap()) })
}

fun CorpusFormat.toCorpusConfig() = CorpusConfiguration(
        corpusName,
        indexes.mapValues { (key, _) -> Index(key, "") },
        entities.mapValues { (entityName, entityFormat) ->
            Entity(
                    entityName,
                    attributes = entityFormat.attributes.mapValues { (attributeName, _) -> cz.vutbr.fit.knot.enticing.dto.config.dsl.Attribute(attributeName) }.toMutableMap()
            )
        }
)

/**
 * Format of one attribute
 *
 * Basically kotlin's pair, only created to allow for custom names instead of first na second
 */
data class EntityFormat(
        val description: Description,
        val attributes: Map<Attribute, Description>
)

infix fun Description.withAttributes(attributes: Map<Attribute, Description>) = EntityFormat(this, attributes)

/**
 * Convert corpus configuration to Corpusformat
 */
fun CorpusConfiguration.toCorpusFormat() =
        CorpusFormat(
                corpusName,
                indexes.mapValues { (_, index) -> index.description },
                entities.mapValues { (_, entity) -> entity.description withAttributes entity.attributes.mapValues { (_, attribute) -> attribute.description } }
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
            val attributes = mutableMapOf<String, String>()
            for ((attribute, description) in entityFormat.attributes) {
                if (rest.all { it.entities[entityName]!!.attributes.containsKey(attribute) }) {
                    attributes[attribute] = description
                }
            }
            newEntities[entityName] = EntityFormat(entityFormat.description, attributes)
        }
    }
    val newName = formats.map { it.corpusName }.distinct().joinToString("-")
    return CorpusFormat(newName, newIndexes, newEntities)
}