package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.EnticingConfigurationUnit
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor.EnticingConfigurationVisitor

data class EntityConfiguration(
        /**
         * name of the index
         */
        var name: String,

        /**
         * details about that index
         */
        var description: String = "",

        var attributes: MutableMap<String, AttributeConfiguration> = mutableMapOf()
) : EnticingConfigurationUnit {

    internal lateinit var metadataConfiguration: MetadataConfiguration

    fun attributes(block: AttributeList.() -> Unit) {
        val attributeList = AttributeList(metadataConfiguration).apply(block).attributes
        for (attribute in attributeList)
            attributes[attribute.name] = attribute
    }

    fun attributes(vararg names: String) {
        check(names.size <= metadataConfiguration.attributeLimit) { "too many attributes (${names.size}), at most ${metadataConfiguration.attributeLimit} is allowed" }
        attributes {
            for (name in names)
                attribute(name)
        }
    }

    override fun accept(visitor: EnticingConfigurationVisitor) {
        visitor.visitEntityConfiguration(this)
    }
}


class EntityList(val metadataConfiguration: MetadataConfiguration, val entities: MutableList<EntityConfiguration> = mutableListOf()) {

    fun entity(name: String, block: EntityConfiguration.() -> Unit) {
        val entity = EntityConfiguration(name)
        entity.metadataConfiguration = metadataConfiguration
        entity.apply(block)
        entities.add(entity)
    }

    fun extraAttributes(vararg names: String) {
        val indexes = names.map {
            metadataConfiguration.indexes[it] ?: throw IllegalStateException("Unknown index $it")
        }

        for (entity in entities) {
            for (index in indexes) {
                if (index.name !in entity.attributes) {
                    val attribute = AttributeConfiguration(index.name, index.description)
                    attribute.index = index
                    entity.attributes[index.name] = attribute
                }
            }
        }
    }

}


