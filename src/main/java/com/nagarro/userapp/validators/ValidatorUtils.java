package com.nagarro.userapp.validators;

import com.nagarro.userapp.dto.UserCreationDTO;
import com.nagarro.userapp.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidatorUtils {
    public static void validateUserCreationDTO(UserCreationDTO userRequestBody) throws ValidationException {
        Validator numericValidator = ValidatorFactory.getValidator("numeric");

        try {
            int size = userRequestBody.getSize();

            if (!numericValidator.validate(String.valueOf(size)) || size <= 0 || size > 5) {
                throw new ValidationException("Invalid size value. Size value must be between 1 to 5 inclusive.");
            }
        } catch (Exception ex) {
            throw new ValidationException("Invalid User creation request payload", ex.getCause());
        }
    }

    public static void validateUserParams(String sortType, String sortOrder, String s_limit, String s_offset) throws ValidationException {
        validateSortType(sortType);
        validateSortOrder(sortOrder);
        validateLimits(s_limit);
        validateOffset(s_offset);

    }

    private static void validateSortType(String sortType) throws ValidationException {
        Validator alphabetsValidator = ValidatorFactory.getValidator("alphabets");

        if (!alphabetsValidator.validate(sortType)) {
            throw new ValidationException("Invalid sort type query value");
        } else if ((!sortType.equalsIgnoreCase("Age")
                && !sortType.equalsIgnoreCase("Name"))) {
            throw new ValidationException("Invalid sort type query value");
        }
    }

    private static void validateSortOrder(String sortOrder) throws ValidationException {
        Validator alphabetsValidator = ValidatorFactory.getValidator("alphabets");

        if (!alphabetsValidator.validate(sortOrder)) {
            throw new ValidationException("Invalid sort order query value");
        } else if ((!sortOrder.equalsIgnoreCase("even")
                && !sortOrder.equalsIgnoreCase("odd"))) {
            throw new ValidationException("Invalid sort order query value");
        }

    }


    private static void validateLimits(String s_limit) throws ValidationException {
        Validator numericValidator = ValidatorFactory.getValidator("numeric");


        if (!numericValidator.validate(String.valueOf(s_limit))) {
            throw new ValidationException("Invalid limit query value. Please provide a numeric value for limit.");
        }

        int limit = Integer.parseInt(s_limit);

        if (limit <= 0 || limit > 5) {
            throw new ValidationException("Invalid limit query value. Limit value must be between 0 and 5   inclusive");
        }
    }

    private static void validateOffset(String s_offset) throws ValidationException {
        Validator numericValidator = ValidatorFactory.getValidator("numeric");
        if (!numericValidator.validate(String.valueOf(s_offset))) {
            throw new ValidationException("Invalid offset query value. Please provide a numeric value for offset.");
        }
        int offset = Integer.parseInt(s_offset);

        if (!numericValidator.validate(String.valueOf(offset)) || offset < 0 || offset > 5) {
            throw new ValidationException("Invalid offset query value. Offset value must be between 0 and 5 inclusive");
        }
    }

}
