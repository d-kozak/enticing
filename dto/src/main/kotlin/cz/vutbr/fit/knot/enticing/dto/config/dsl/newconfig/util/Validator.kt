package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.util

import java.io.File


interface Validator {

    /**
     * errors encountered so far
     */
    val errors: MutableList<String>


    /**
     * Check whether given condition is true, if not, error message is created and saved into the error list
     */
    fun check(condition: Boolean, messageFactory: () -> String) = if (!condition) {
        errors.add(messageFactory())
        false
    } else true


    fun checkNotEmpty(string: String, name: String) {
        if (string.isEmpty()) errors.add("$name should not be empty")
    }

    fun checkNotEmpty(collection: Collection<*>, name: String) {
        if (collection.isEmpty()) errors.add("$name should not be empty")
    }

    fun checkDirectory(directory: File, createIfNecessary: Boolean = false): Boolean {
        if (createIfNecessary) {
            if (!directory.exists()) {
                if (!directory.mkdir()) {
                    errors.add("Could not create directory $directory")
                    return false
                }
            } else {
                if (!directory.isDirectory) {
                    errors.add("$directory is not a directory")
                    return false
                }

            }
        } else {
            if (!directory.isDirectory) {
                errors.add("$directory is not a directory")
                return false
            } else if (!directory.exists()) {
                errors.add("$directory does not exist")
                return false
            }
        }
        return true
    }

    fun checkMg4jFiles(files: List<File>): Boolean {
        var isValid = true
        if (files.isEmpty()) {
            errors.add("No mg4j files specified")
            isValid = false
        }

        for (file in files) {
            if (!file.exists()) {
                errors.add("File $file does not exist")
                isValid = false
            } else if (!file.name.endsWith(".mg4j")) {
                errors.add("File $file does not have the proper .mg4j extension")
                isValid = false
            }
        }

        return isValid
    }

    fun requireNoErrors() {
        if (errors.isNotEmpty())
            throw IllegalStateException(errors.joinToString("\n"))
    }
}