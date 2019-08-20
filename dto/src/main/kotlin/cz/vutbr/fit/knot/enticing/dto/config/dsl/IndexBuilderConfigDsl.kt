package cz.vutbr.fit.knot.enticing.dto.config.dsl

import java.io.File

fun indexBuilder(block: IndexBuilderConfig.() -> Unit): IndexBuilderConfig = IndexBuilderConfig().apply(block)

class IndexBuilderConfig {

    lateinit var input: List<File>
    lateinit var output: File
    lateinit var corpusConfiguration: CorpusConfiguration

    /**
     * for errors that were discovered during creation, but their test cannot be postponed so easily
     *
     * e.g. mg4j directory
     */
    private val errors = mutableListOf<String>()

    val indexes
        get() = corpusConfiguration.indexes.values.toList()

    fun inputFiles(vararg files: String) {
        inputFiles(files.toList())
    }

    fun inputFiles(files: List<String>) {
        this.input = files.map { File(it) }
    }

    fun inputDirectory(path: String) {
        val directory = File(path)
        checkDirectory(directory, errors)
        if (errors.isEmpty())
            this.input = directory.listFiles { _, name -> name.endsWith(".mg4j") }?.toList() ?: emptyList()
        else
            this.input = emptyList()
    }

    fun outputDirectory(path: String) {
        this.output = File(path)
    }

    fun corpus(name: String, block: CorpusConfiguration.() -> Unit): CorpusConfiguration = corpusConfig(name, block).also {
        this.corpusConfiguration = it
    }


    fun validate(): List<String> {
        val errors = errors.toMutableList()

        checkMg4jFiles(input, errors)
        checkDirectory(this.output, errors, createIfNecessary = true)
        corpusConfiguration.validate(errors)

        return errors
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IndexBuilderConfig) return false

        if (input != other.input) return false
        if (output != other.output) return false
        if (corpusConfiguration != other.corpusConfiguration) return false

        return true
    }

    override fun hashCode(): Int {
        var result = input.hashCode()
        result = 31 * result + output.hashCode()
        result = 31 * result + corpusConfiguration.hashCode()
        return result
    }

    override fun toString(): String = buildString {
        append("Indexer config {\n")
        append("\tinput: $input\n")
        append("\toutput: $output\n")
        append("\tcorpus: ${corpusConfiguration.corpusName}\n")
        append("\tindexes:\n")
        for (index in corpusConfiguration.indexes.values) {
            append("\t\t${index.name}:[${index.description},${index.columnIndex}]\n")
        }
        append("\tentities:\n")
        for (entity in corpusConfiguration.entities.values) {
            append("\t\t${entity.name}, attributes: ${entity.attributes.values.map { it.name + ": '" + it.correspondingIndex + "'," + it.columnIndex }}\n")
        }
        append("}\n")
    }
}

