package cz.vutbr.fit.knot.enticing.dto.config.dsl.metadata

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfigurationUnit
import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationVisitor

data class EntityConfiguration(
        /**
         * name of the index
         */
        var name: String,
        var parentEntityName: String? = null,
        /**
         * details about that index
         */
        var description: String = "",

        var ownAttributes: MutableMap<String, AttributeConfiguration> = mutableMapOf()
) : EnticingConfigurationUnit {

    internal lateinit var metadata: MetadataConfiguration
    internal lateinit var metadataConfiguration: MetadataConfiguration

    lateinit var allAttributes: MutableMap<String, AttributeConfiguration>

    internal fun attributesResolved() = ::allAttributes.isInitialized

    lateinit var fullName: String

    fun attributes(block: AttributeList.() -> Unit) {
        val attributeList = AttributeList(metadataConfiguration).apply(block).attributes
        for (attribute in attributeList)
            ownAttributes[attribute.name] = attribute
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

    infix fun String.with(attributes: Array<out String>) {
        val entity = EntityConfiguration(this)
        entity.metadataConfiguration = metadataConfiguration
        entity.attributes(*attributes)
        entities.add(entity)
    }

    fun attributes(vararg names: String) = names.copyOf()

    fun extraAttributes(vararg names: String) {
        val indexes = names.map {
            metadataConfiguration.indexes[it] ?: throw IllegalStateException("Unknown index $it")
        }

        for (entity in entities) {
            for (index in indexes) {
                if (index.name !in entity.ownAttributes) {
                    val attribute = AttributeConfiguration(index.name, index.description)
                    attribute.index = index
                    attribute.attributeIndex = entity.ownAttributes.size
                    entity.ownAttributes[index.name] = attribute
                }
            }
        }
    }

}


