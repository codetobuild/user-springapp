package com.nagarro.userapp.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumericValidator implements Validator{
    private static final NumericValidator INSTANCE = new NumericValidator();
    private static final String NUMERIC_PATTERN = "^[0-9]+$";

    private NumericValidator() { // prevent instantiation
    }

    public static NumericValidator getInstance() {
        return INSTANCE;
    }
    @Override
    public boolean validate(String input) {
        System.out.println("input" + input);
        if (input == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(NUMERIC_PATTERN);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
