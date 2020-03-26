package com.hatef.swedbank.loanapi.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = ManagerNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ManagerNumberConstraint {
    String message() default "Invalid number of managers; should be in range:[1-3]";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
