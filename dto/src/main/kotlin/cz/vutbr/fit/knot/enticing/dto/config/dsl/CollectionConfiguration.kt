package cz.vutbr.fit.knot.enticing.dto.config.dsl

import java.io.File

class CollectionConfiguration(val name: String) {

    lateinit var mg4jFiles: List<File>
    lateinit var indexDirectory: File

    /**
     * for errors that were discovered during creation, but their test cannot be postponed so easily
     *
     * e.g. mg4j directory
     */
    private val errors = mutableListOf<String>()

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

    fun validate(errors: MutableList<String>) {
        checkMg4jFiles(mg4jFiles, errors)
        checkDirectory(this.indexDirectory, errors)
    }
}