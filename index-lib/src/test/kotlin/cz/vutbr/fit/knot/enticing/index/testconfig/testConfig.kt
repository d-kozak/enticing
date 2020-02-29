package cz.vutbr.fit.knot.enticing.index.testconfig

import cz.vutbr.fit.knot.enticing.dto.config.dsl.EnticingConfiguration
import cz.vutbr.fit.knot.enticing.dto.config.dsl.validateOrFail
import cz.vutbr.fit.knot.enticing.dto.config.executeScript

val loadedConfiguration = executeScript<EnticingConfiguration>("../dto/src/test/resources/config.kts")
        .validateOrFail()

val dummyMetadataConfiguration = loadedConfiguration.corpuses.values.first().metadataConfiguration