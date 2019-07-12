package cz.vutbr.fit.knot.enticing.dto.config.dsl

import java.io.File

fun indexClient(block: IndexClientConfig.() -> Unit): IndexClientConfig = IndexClientConfig().apply(block)

open class IndexClientConfig {


    lateinit var mg4jFiles: List<File>
    lateinit var indexDirectory: File
    lateinit var corpusConfiguration: CorpusConfiguration

    /**
     * for errors that were discovered during creation, but their test cannot be postponed so easily
     *
     * e.g. mg4j directory
     */
    private val errors = mutableListOf<String>()

    val indexes
        get() = corpusConfiguration.indexes.values.toList()

    fun mg4jFiles(vararg files: String) {
        mg4jFiles(files.toList())
    }

    fun mg4jFiles(files: List<String>) {
        this.mg4jFiles = files.map { File(it) }
    }

    fun mg4jDirectory(path: String) {
        val inputDirectory = File(path)
        checkDirectory(inputDirectory, errors)
        if (errors.isEmpty()) {
            this.mg4jFiles = inputDirectory.listFiles { _, name -> name.endsWith(".mg4j") }?.toList() ?: emptyList()
        } else {
            this.mg4jFiles = emptyList()
        }
    }

    fun indexDirectory(path: String) {
        this.indexDirectory = File(path)
    }

    fun corpus(name: String, block: CorpusConfiguration.() -> Unit): CorpusConfiguration = corpusConfig(name, block).also {
        this.corpusConfiguration = it
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IndexClientConfig) return false

        if (mg4jFiles != other.mg4jFiles) return false
        if (indexDirectory != other.indexDirectory) return false
        if (corpusConfiguration != other.corpusConfiguration) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mg4jFiles.hashCode()
        result = 31 * result + indexDirectory.hashCode()
        result = 31 * result + corpusConfiguration.hashCode()
        return result
    }

    override fun toString(): String = buildString {
        append("Index client config {\n")
        append("\tmg4jFiles: $mg4jFiles\n")
        append("\tindexDirectory: $indexDirectory\n")
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
        val errors = errors.toMutableList()
        checkMg4jFiles(mg4jFiles, errors)
        checkDirectory(this.indexDirectory, errors)
        corpusConfiguration.validate(errors)
        return errors
    }
}

