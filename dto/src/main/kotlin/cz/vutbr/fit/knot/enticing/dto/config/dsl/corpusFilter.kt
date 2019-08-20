package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.Entities
import cz.vutbr.fit.knot.enticing.dto.EntityId
import cz.vutbr.fit.knot.enticing.dto.Indexes
import cz.vutbr.fit.knot.enticing.dto.TextMetadata

fun CorpusConfiguration.filterBy(metadata: TextMetadata, defaultIndex: String): CorpusConfiguration = when (metadata) {
    is TextMetadata.Predefined -> filterPredefined(metadata, defaultIndex)
    is TextMetadata.ExactDefinition -> filterExact(metadata, defaultIndex)
}

private fun CorpusConfiguration.filterPredefined(metadata: TextMetadata.Predefined, defaultIndex: String): CorpusConfiguration = when (metadata.value) {
    "all" -> this
    "none" -> this.copy(entities = emptyMap(), indexes = onlySpecifiedIndexes(defaultIndex))
    else -> throw IllegalArgumentException("Unknown toplevel format ${metadata.value}")
}


private fun CorpusConfiguration.filterExact(metadata: TextMetadata.ExactDefinition, defaultIndex: String): CorpusConfiguration = this.copy(
        indexes = filterIndexes(metadata.indexes, defaultIndex),
        entities = filterEntities(metadata.entities)
)

private fun CorpusConfiguration.filterEntities(entities: Entities): Map<String, Entity> = when (entities) {
    is Entities.Predefined -> when (entities.value) {
        "all" -> this.entities
        "none" -> emptyMap()
        else -> throw IllegalArgumentException("Unknown entity format ${entities.value}")
    }
    is Entities.ExactDefinition -> entities.entities.entries.map { (name, indexes) ->
        name to when (indexes) {
            is Indexes.Predefined -> {
                when (indexes.value) {
                    "all" -> getEntityOrDie(name)
                    "none" -> getEntityOrDie(name).copy(attributes = mutableMapOf())
                    else -> throw IllegalArgumentException("Unknown entity attribute format ${indexes.value}")
                }
            }
            is Indexes.ExactDefinition -> {
                val entity = getEntityOrDie(name)
                val attributes = indexes.names
                        .map {
                            it to (entity.attributes[it]
                                    ?: throw IllegalArgumentException("Unknown attribute $it of entity $name"))
                        }
                        .toMap()
                        .toMutableMap()
                entity.copy(attributes = attributes)
            }
        }
    }.toMap()
}


private fun CorpusConfiguration.getEntityOrDie(name: EntityId) =
        this.entities[name] ?: throw IllegalArgumentException("Unknown entity $name")


private fun CorpusConfiguration.filterIndexes(indexes: Indexes, defaultIndex: String): Map<String, Index> = when (indexes) {
    is Indexes.Predefined -> when (indexes.value) {
        "all" -> this.indexes
        "none" -> onlySpecifiedIndexes(defaultIndex)
        else -> throw IllegalArgumentException("Unknown index format ${indexes.value}")
    }

    is Indexes.ExactDefinition -> if (defaultIndex in indexes.names)
        onlySpecifiedIndexes(indexes.names)
    else {
        val names = indexes.names.toMutableList()
        names.add(0, defaultIndex)
        onlySpecifiedIndexes(names)
    }
}


private fun CorpusConfiguration.onlySpecifiedIndexes(indexes: List<String>): Map<String, Index> = indexes.map {
    it to (this.indexes[it] ?: throw IllegalArgumentException("Could not find index $it"))
}.toMap()

private fun CorpusConfiguration.onlySpecifiedIndexes(vararg indexes: String) = onlySpecifiedIndexes(indexes.toList())