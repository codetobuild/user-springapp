package com.nagarro.userapp.validators;

import com.nagarro.userapp.dto.UserCreationDTO;
import com.nagarro.userapp.exceptions.ValidationException;

public class ValidatorUtils
{
    public static void validateUserCreationDTO(UserCreationDTO userRequestBody) throws ValidationException {
        Validator numericValidator = ValidatorFactory.getValidator("numeric");

        try {
            int size = userRequestBody.getSize();

            if (!numericValidator.validate(String.valueOf(size)) || size <= 0 || size > 5) {
                System.out.println("Invalid" + numericValidator.validate(String.valueOf(size)));
                throw new ValidationException("Invalid size value. Size value must be between 1 to 5 inclusive.");
            }
        } catch (Exception ex) {
            throw new ValidationException("Error during validation", ex.getCause());
        }
    }

    public static void validateUserParams(String sortType, String sortOrder, int limit, int offset) throws ValidationException {
        validateSortType(sortType);
        validateSortOrder(sortOrder);
        validateLimit(limit);
        validateOffset(offset);
    }

    private static void validateSortType(String sortType) throws ValidationException {
        Validator alphabetsValidator = ValidatorFactory.getValidator("alphabets");

            if (!alphabetsValidator.validate(sortType)) {
                System.out.println("first errror");
                throw new ValidationException("Invalid sort type query value");
            } else if ((!sortType.equalsIgnoreCase("Age") && !sortType.equalsIgnoreCase("Name"))) {
                System.out.println("sec errror" + sortType );

                throw new ValidationException("Invalid sort type query value");
            }
    }

    private static void validateSortOrder(String sortOrder) throws ValidationException {
        Validator alphabetsValidator = ValidatorFactory.getValidator("alphabets");

        try {
            if (!alphabetsValidator.validate(sortOrder)) {
                throw new ValidationException("Invalid sort order query value");
            } else if ((!sortOrder.equalsIgnoreCase("even")
                        && !sortOrder.equalsIgnoreCase("odd"))) {
                throw new ValidationException("Invalid sort order query value");
            }
        } catch (Exception ex) {
            throw new ValidationException("Error during sort order validation", ex.getCause());
        }
    }

    private static void validateLimit(int limit) throws ValidationException {
        Validator numericValidator = ValidatorFactory.getValidator("numeric");

        try {
            if (!numericValidator.validate(String.valueOf(limit)) || limit <= 0 || limit > 5) {
                throw new ValidationException("Invalid limit query value");
            }
        } catch (Exception ex) {
            throw new ValidationException("Error during limit validation", ex.getCause());
        }
    }

    private static void validateOffset(int offset) throws ValidationException {
        Validator numericValidator = ValidatorFactory.getValidator("numeric");

        try {
            if (!numericValidator.validate(String.valueOf(offset)) || offset < 0 || offset > 5) {
                throw new ValidationException("Invalid offset query value");
            }
        } catch (Exception ex) {
            throw new ValidationException("Error during offset validation", ex.getCause());
        }
    }

}
