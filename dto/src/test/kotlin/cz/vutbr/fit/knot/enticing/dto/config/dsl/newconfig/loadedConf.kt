package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

import cz.vutbr.fit.knot.enticing.dto.config.executeScript

val loadedConfiguration = executeScript<EnticingConfiguration>("../dto/src/test/resources/config.kts")
        .validateOrFail()