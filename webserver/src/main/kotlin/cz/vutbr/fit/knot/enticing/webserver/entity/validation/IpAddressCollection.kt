package cz.vutbr.fit.knot.enticing.webserver.entity.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [IpAddressCollectionValidator::class])
annotation class IpAddressCollection(
        val message: String = "Invalid Ip address collection",
        val groups: Array<KClass<Any>> = [],
        val payload: Array<KClass<Payload>> = []
)