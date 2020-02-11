package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.metadata.MetadataConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.util.Validator
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.util.ValidatorImpl
import java.io.File

/**
 * Represents transident configurations that are passed from one module to another as parameters
 * Their validation should happen immeditally upon creation and should be faily cheap
 */
interface TransientConfigurationUnit : Validator {
    fun validateOrFail()
}

val File.mg4jFiles: List<File>
    get() = this.listFiles { file -> file.name.endsWith(".mg4j") }?.toList() ?: emptyList()

data class IndexBuilderConfig(
        val corpusName: String,
        val collectionName: String,
        val mg4jDir: File,
        val indexDir: File,
        val metadataConfiguration: MetadataConfiguration
) : TransientConfigurationUnit, Validator by ValidatorImpl() {

    init {
        validateOrFail()
    }

    override fun validateOrFail() {
        checkNotEmpty(corpusName, "corpusName")
        checkNotEmpty(collectionName, "collectionName")
        checkDirectory(mg4jDir)
        checkNotEmpty(mg4jDir.mg4jFiles, "mg4jFiles")
        checkDirectory(indexDir, createIfNecessary = true)
        requireNoErrors()
    }
}


data class CollectionManagerConfiguration(
        val corpusName: String,
        val collectionName: String,
        val mg4jDir: File,
        val indexDir: File,
        val metadataConfiguration: MetadataConfiguration
) : TransientConfigurationUnit, Validator by ValidatorImpl() {

    init {
        validateOrFail()
    }

    override fun validateOrFail() {
        checkNotEmpty(corpusName, "corpusName")
        checkNotEmpty(collectionName, "collectionName")
        checkDirectory(mg4jDir)
        checkNotEmpty(mg4jDir.mg4jFiles, "mg4jFiles")
        checkDirectory(indexDir)
        requireNoErrors()
    }
}