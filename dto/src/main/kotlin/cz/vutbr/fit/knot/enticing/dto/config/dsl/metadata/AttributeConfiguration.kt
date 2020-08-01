package cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfigurationUnit
import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationVisitor

/**
 * Configuration for on attribute of an entity
 */
data class AttributeConfiguration(
        /**
         * name of the index
         */
        var name: String,

        /**
         * details about that index
         */
        var description: String = ""
) : EnticingConfigurationUnit {
    /**
     * corresponding index
     */
    lateinit var index: IndexConfiguration

    /**
     * Index in the ordering of attributes(used for mapping them to the param* indexes)
     */
    var attributeIndex: Int = 0

    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitAttributeConfiguration(this)
    }

    fun copyOf(): AttributeConfiguration {
        val res = AttributeConfiguration(name, description)
        if (this::index.isInitialized)
            res.index = index
        res.attributeIndex = attributeIndex
        return res
    }
}

class AttributeList(var metadataConfiguration: MetadataConfiguration, val attributes: MutableList<AttributeConfiguration> = mutableListOf()) {

    private var indexIterator: Iterator<IndexConfiguration>

    init {
        var values = metadataConfiguration.indexes.values.asSequence()
        if (metadataConfiguration.attributeIndexes.first > 0)
            values = values.drop(metadataConfiguration.attributeIndexes.first)
        indexIterator = values.take(metadataConfiguration.attributeLimit)
                .iterator()
    }

    fun attribute(name: String, block: AttributeConfiguration.() -> Unit = {}) {
        check(indexIterator.hasNext()) { "too many attributes in $name, limit is ${metadataConfiguration.attributeLimit}" }
        val newAttribute = AttributeConfiguration(name).apply(block)
                .also {
                    it.index = indexIterator.next()
                    it.attributeIndex = attributes.size
                }
        attributes.add(newAttribute)
    }

}

