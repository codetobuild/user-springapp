package com.nagarro.userapp.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnglishAlphabetsValidator implements Validator{
    private static final EnglishAlphabetsValidator INSTANCE = new EnglishAlphabetsValidator();
    private static final String ENGLISH_ALPHABETS_PATTERN = "^[a-zA-Z]+$";
    private EnglishAlphabetsValidator() {
    }

    public static EnglishAlphabetsValidator getInstance() {
        return INSTANCE;
    }
    @Override
    public boolean validate(String input) {
        if (input == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(ENGLISH_ALPHABETS_PATTERN);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
