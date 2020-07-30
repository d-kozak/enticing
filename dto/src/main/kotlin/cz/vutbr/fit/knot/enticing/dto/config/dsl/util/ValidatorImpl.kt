package cz.vutbr.fit.knot.enticing.dto.config.dsl.util

/**
 * Since all the logic is already in the interface, the actual implementation just contains the list for storing errors
 */
data class ValidatorImpl(override val errors: MutableList<String> = mutableListOf()) : Validator