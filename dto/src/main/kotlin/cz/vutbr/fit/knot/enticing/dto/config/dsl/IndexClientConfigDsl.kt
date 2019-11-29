package cz.vutbr.fit.knot.enticing.dto.config.dsl

fun indexClient(block: IndexClientConfig.() -> Unit): IndexClientConfig = IndexClientConfig().apply(block)

fun MutableList<CollectionConfiguration>.collection(name: String, block: CollectionConfiguration.() -> Unit) {
    val config = CollectionConfiguration(name)
    config.apply(block)
    this.add(config)
}

open class IndexClientConfig {

    val collections = mutableListOf<CollectionConfiguration>()

    lateinit var logDirectory: String

    lateinit var corpusConfiguration: CorpusConfiguration

    val indexes
        get() = corpusConfiguration.indexes.values.toList()


    fun logInto(directory: String) {
        this.logDirectory = directory
    }

    fun collections(block: MutableList<CollectionConfiguration>.() -> Unit) {
        this.collections.apply(block)
    }


    fun corpus(name: String, block: CorpusConfiguration.() -> Unit): CorpusConfiguration = corpusConfig(name, block).also {
        this.corpusConfiguration = it
    }


    override fun toString(): String = buildString {
        append("Index client config {\n")
        append("\tcollections: $collections\n")
        append("\tcorpus: ${corpusConfiguration.corpusName}\n")
        append("\tindexes:\n")
        for (index in corpusConfiguration.indexes.values) {
            append("\t\t${index.name}\n")
        }
        append("\tentities:\n")
        for (entity in corpusConfiguration.entities.values) {
            append("\t\t${entity.name}, attributes: ${entity.attributes.values.map { it.name }}\n")
        }
        append("}\n")
    }

    fun validate(): List<String> {
        val errors = mutableListOf<String>()
        if (collections.isEmpty()) {
            errors.add("No collections specified, at least one is necessary")
        }

        if (!::logDirectory.isInitialized) {
            errors.add("No log directory was specified")
        }

        collections.forEach { it.validate(errors) }
        corpusConfiguration.validate(errors)
        return errors
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IndexClientConfig) return false

        if (collections != other.collections) return false
        if (corpusConfiguration != other.corpusConfiguration) return false

        return true
    }

    override fun hashCode(): Int {
        var result = collections.hashCode()
        result = 31 * result + corpusConfiguration.hashCode()
        return result
    }
}

