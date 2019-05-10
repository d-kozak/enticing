package cz.vutbr.fit.knot.enticing.webserver.entity.ipaddress;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = IpAddressCollectionValidator.class)
@Documented
public @interface IpAddressCollection {
    String message() default "Invalid Ip address collection";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
