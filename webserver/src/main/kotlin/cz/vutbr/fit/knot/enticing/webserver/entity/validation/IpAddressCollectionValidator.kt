package cz.vutbr.fit.knot.enticing.webserver.entity.validation

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

const val ipRegex = """^(((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))|localhost)(:\d+)?${'$'}"""

class IpAddressCollectionValidator : ConstraintValidator<IpAddressCollection, Collection<String>> {

    private val pattern = ipRegex.toPattern()

    override fun isValid(ipSet: Collection<String>, context: ConstraintValidatorContext): Boolean = ipSet.all { pattern.matcher(it).find() }
}