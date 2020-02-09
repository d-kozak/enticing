package cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig

import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.util.ValidatorImpl
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor.EnticingConfigurationValidator
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor.EnticingConfigurationVisitee
import cz.vutbr.fit.knot.enticing.dto.config.dsl.newconfig.visitor.EnticingConfigurationWalker

/**
 * general configuration interface
 */
interface EnticingConfigurationUnit : EnticingConfigurationVisitee {

}


fun <T : EnticingConfigurationUnit> T.validateOrFail(): T {
    val validator = EnticingConfigurationValidator(ValidatorImpl())
    val walker = EnticingConfigurationWalker(validator)
    this.accept(walker)
    if (validator.errors.isNotEmpty())
        throw IllegalStateException(validator.errors.joinToString("\n"))
    return this
}

