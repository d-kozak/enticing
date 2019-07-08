package cz.vutbr.fit.knot.enticing.dto.config.dsl

import java.io.File

internal fun checkDirectory(directory: File, errors: MutableList<String>, createIfNecessary: Boolean = false) {
    if (createIfNecessary) {
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                errors.add("Could not create directory $directory")
            }
        } else {
            if (!directory.isDirectory) {
                errors.add("$directory is not a directory")
            }

        }
    } else {
        if (!directory.isDirectory) {
            errors.add("$directory is not a directory")
        }
        if (!directory.exists()) {
            errors.add("$directory does not exist")
        }
    }
}

internal fun checkMg4jFiles(files: List<File>, errors: MutableList<String>) {
    if (files.isEmpty()) {
        errors.add("No mg4j files specified")
    }
    errors.addAll(files.filter { !it.exists() }
            .map { "File $it does not exist" })
    files.filter { !it.name.endsWith(".mg4j") }
            .map { "File $it does not have the proper .mg4j extension" }
}