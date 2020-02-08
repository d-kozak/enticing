package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfigurationUnit
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor.EnticingConfigurationVisitor

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

    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitAttributeConfiguration(this)
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
                .also { it.index = indexIterator.next() }
        attributes.add(newAttribute)
    }

}

