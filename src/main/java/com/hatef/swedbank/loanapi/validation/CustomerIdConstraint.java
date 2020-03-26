package com.hatef.swedbank.loanapi.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = CustomerIdValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomerIdConstraint {
    String message() default "Invalid customer id format : it should follow XX-XXXX-XXX pattern";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
