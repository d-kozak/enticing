package cz.vutbr.fit.knot.enticing.index.config.dsl

import java.io.File

fun indexerConfiguration(block: IndexerConfig.() -> Unit): IndexerConfig = IndexerConfig().apply(block)

class IndexerConfig {

    lateinit var input: List<File>
    lateinit var output: File
    lateinit var corpusConfiguration: CorpusConfiguration

    fun inputFiles(vararg files: String) {
        this.input = files.map { File(it) }
    }

    fun inputDirectory(directoryPath: String) {
        val directory = File(directoryPath)
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
        if (other !is IndexerConfig) return false

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

    override fun toString(): String {
        return "IndexerConfig(input=$input, output=$output, corpusConfiguration=$corpusConfiguration)"
    }
}

