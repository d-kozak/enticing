package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata

data class IndexConfiguration(
        /**
         * name of the index
         */
        var name: String,

        /**
         * details about that index
         */
        var description: String = "",
        /**
         * Index of the column this index is located at
         */
        var columnIndex: Int = 0
)


class IndexList(val metadataConfiguration: MetadataConfiguration, val indexes: MutableList<IndexConfiguration> = mutableListOf()) {
    fun index(name: String, block: IndexConfiguration.() -> Unit = {}) {
        indexes.add(IndexConfiguration(name, columnIndex = indexes.size).apply(block))
    }

    fun attributeIndexes(count: Int) {
        check(count > 0) { "positive value required,was given $count" }
        metadataConfiguration.attributeIndexes = indexes.size until indexes.size + count
        for (i in 0 until count)
            index("param$i")
    }
}

