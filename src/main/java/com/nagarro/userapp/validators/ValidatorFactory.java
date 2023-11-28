package com.nagarro.userapp.validators;

public class ValidatorFactory {
    public static Validator getValidator(String validatorType){
        if ("numeric".equalsIgnoreCase(validatorType)) {
            return NumericValidator.getInstance();
        } else if ("alphabets".equalsIgnoreCase(validatorType)) {
            return EnglishAlphabetsValidator.getInstance();
        } else {
            throw new IllegalArgumentException("Unsupported parameter type: " + validatorType);
        }
    }
}
