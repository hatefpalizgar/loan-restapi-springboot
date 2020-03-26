package com.hatef.swedbank.loanapi.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CustomerIdValidator implements ConstraintValidator<CustomerIdConstraint, String> {
    
    @Override
    public void initialize(CustomerIdConstraint constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(String customerIdField, ConstraintValidatorContext context) {
        return customerIdField != null && customerIdField.matches("(\\w{2})-(\\w{4})-(\\w{3})");
    }
}
