package cz.vutbr.fit.knot.enticing.dto.config.dsl

import cz.vutbr.fit.knot.enticing.dto.config.dsl.util.ValidatorImpl
import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationValidator
import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationVisitee
import cz.vutbr.fit.knot.enticing.dto.config.dsl.visitor.EnticingConfigurationWalker

/**
 * General configuration interface
 */
interface EnticingConfigurationUnit : EnticingConfigurationVisitee {

}

/**
 * Validate given configuration unit
 * @throws IllegalStateException if the configuration is invalid
 */
fun <T : EnticingConfigurationUnit> T.validateOrFail(): T {
    val validator = EnticingConfigurationValidator(ValidatorImpl())
    val walker = EnticingConfigurationWalker(validator)
    this.accept(walker)
    if (validator.errors.isNotEmpty())
        throw IllegalStateException(validator.errors.joinToString("\n"))
    return this
}

