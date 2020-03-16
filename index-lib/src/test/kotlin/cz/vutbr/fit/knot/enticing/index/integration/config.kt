package cz.vutbr.fit.knot.enticing.index.integration

import cz.vutbr.fit.knot.enticing.dto.config.dsl.CollectionManagerConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.IndexBuilderConfig
import cz.vutbr.fit.knot.enticing.dto.format.text.TextUnit
import cz.vutbr.fit.knot.enticing.index.mg4j.fullTestMetadataConfig
import java.io.File

val builderConfig = IndexBuilderConfig(
        "CC",
        "col1",
        File("../data/mg4j"),
        File("../data/indexed"),
        fullTestMetadataConfig
)


val collectionManagerConfiguration = CollectionManagerConfiguration(
        "CC",
        "col1",
        File("../data/mg4j"),
        File("../data/indexed"),
        fullTestMetadataConfig
)

val metadata = collectionManagerConfiguration.metadataConfiguration

val tokenIndex = metadata.indexOf("token")

val TextUnit.Word.token: String
    get() = indexes[tokenIndex]