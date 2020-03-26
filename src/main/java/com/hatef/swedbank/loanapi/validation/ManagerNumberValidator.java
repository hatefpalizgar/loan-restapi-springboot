package com.hatef.swedbank.loanapi.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class ManagerNumberValidator implements ConstraintValidator<ManagerNumberConstraint, List<String>> {
    
    @Override
    public void initialize(ManagerNumberConstraint constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        return !value.isEmpty() && value.size() <= 3;
    }
}
