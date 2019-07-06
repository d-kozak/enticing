package cz.vutbr.fit.knot.enticing.dto.config.dsl

import java.io.File

internal fun requireDirectory(path: String, createIfNecessary: Boolean = false): File {
    val directory = File(path)
    if (createIfNecessary) {
        if (!directory.exists()) {
            directory.mkdir() || throw IllegalArgumentException("Could not create directory $path")
        } else {
            directory.isDirectory || throw IllegalArgumentException("$path is not a directory")
        }
    } else {
        directory.isDirectory || throw IllegalArgumentException("$path is not a directory")
        directory.exists() || throw IllegalArgumentException("$path does not exist")
    }
    return directory
}

internal fun requireMg4jFiles(files: List<String>): List<File> {
    val inputFiles = files.map { File(it) }
    val nonExistent = inputFiles.filter { !it.exists() }.toList()
    if (nonExistent.isNotEmpty()) {
        throw IllegalArgumentException("Files $nonExistent do not exist")
    }
    val withoutExtension = inputFiles.filter { !it.name.endsWith(".mg4j") }
    if (withoutExtension.isNotEmpty()) {
        throw IllegalArgumentException("Files $nonExistent do not have the proper .mg4j extension")
    }
    return inputFiles
}