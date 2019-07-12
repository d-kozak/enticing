package cz.vutbr.fit.knot.enticing.dto

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CorpusConfiguration
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