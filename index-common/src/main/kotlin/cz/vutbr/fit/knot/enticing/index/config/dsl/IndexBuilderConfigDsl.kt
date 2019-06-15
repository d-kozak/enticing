package cz.vutbr.fit.knot.enticing.index.config.dsl

import java.io.File

fun indexBuilder(block: IndexBuilderConfig.() -> Unit): IndexBuilderConfig = IndexBuilderConfig().apply(block)

class IndexBuilderConfig {

    lateinit var input: List<File>
    lateinit var output: File
    lateinit var corpusConfiguration: CorpusConfiguration

    val indexes
        get() = corpusConfiguration.indexes.values.toList()

    fun inputFiles(vararg files: String) {
        this.input = files.map { File(it) }
    }

    fun inputDirectory(path: String) {
        val directory = File(path)
        directory.isDirectory || throw IllegalArgumentException("$directory is not a directory")
        this.input = directory.listFiles { _, name -> name.endsWith(".mg4j") }.toList()
    }

    fun outputDirectory(path: String) {
        this.output = File(path)
    }

    fun corpus(name: String, block: CorpusConfiguration.() -> Unit): CorpusConfiguration = corpusDslInternal(name, block).also {
        this.corpusConfiguration = it
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
            append("\t\t${index.name}\n")
        }
        append("\tentities:\n")
        for (entity in corpusConfiguration.entities.values) {
            append("\t\t${entity.name}, attributes: ${entity.attributes.values.map { it.name }}\n")
        }
        append("}\n")
    }
}

