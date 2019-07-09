package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.query.Entities
import cz.vutbr.fit.knot.enticing.dto.query.Indexes
import cz.vutbr.fit.knot.enticing.dto.query.TextMetadata

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
    is Entities.Predefined -> {
        when (entities.value) {
            "all" -> this.entities
            "none" -> emptyMap()
            else -> throw IllegalArgumentException("Unknown entity format ${entities.value}")
        }
    }
    is Entities.ExactDefinition -> {
        // todo finish this
        this.entities
    }
}


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
        names.add(defaultIndex)
        onlySpecifiedIndexes(names)
    }
}


private fun CorpusConfiguration.onlySpecifiedIndexes(indexes: List<String>): Map<String, Index> = indexes.map {
    it to (this.indexes[it] ?: throw IllegalArgumentException("Could not find index $it"))
}.toMap()

private fun CorpusConfiguration.onlySpecifiedIndexes(vararg indexes: String) = onlySpecifiedIndexes(indexes.toList())