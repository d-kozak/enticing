package cz.vutbr.fit.knot.enticing.webserver.entity.validation

import cz.vutbr.fit.knot.enticing.dto.utils.regex.urlRegex
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [UrlCollectionValidator::class])
annotation class UrlCollection(
        val message: String = "Invalid url collection",
        val groups: Array<KClass<Any>> = [],
        val payload: Array<KClass<Payload>> = []
)

class UrlCollectionValidator : ConstraintValidator<UrlCollection, Collection<String>> {

    private val pattern = urlRegex.toPattern()
    override fun isValid(ipSet: Collection<String>, context: ConstraintValidatorContext): Boolean = ipSet.all { pattern.matcher(it).find() }

}
