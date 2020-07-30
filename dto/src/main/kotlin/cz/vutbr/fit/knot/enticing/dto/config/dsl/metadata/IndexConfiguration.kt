package cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfigurationUnit
import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationVisitor

/**
 * Configuration of one index
 */
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
) : EnticingConfigurationUnit {

    /**
     * parent pointer back to the metadata configuration
     */
    internal lateinit var metadata: MetadataConfiguration

    /**
     * synthetic == used only in the code to pass along some metadata, without actual representation in the mg4j files
     */
    val isSynthetic: Boolean
        get() = name.startsWith('_')

    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitIndexConfiguration(this)
    }
}


class IndexList(val metadataConfiguration: MetadataConfiguration, val indexes: MutableList<IndexConfiguration> = mutableListOf()) {
    fun index(name: String, block: IndexConfiguration.() -> Unit = {}) {
        indexes.add(IndexConfiguration(name, columnIndex = indexes.size).apply(block))
    }

    infix fun String.whichIs(description: String) {
        indexes.add(IndexConfiguration(this, description, columnIndex = indexes.size))
    }

    fun attributeIndexes(count: Int) {
        check(count > 0) { "positive value required,was given $count" }
        metadataConfiguration.attributeIndexes = indexes.size until indexes.size + count
        for (i in 0 until count)
            index("param$i")
    }
}

