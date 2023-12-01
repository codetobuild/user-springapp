package com.nagarro.userapp.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.userapp.model.Gender;

import java.io.IOException;

public class GenderUtil {
    public static Gender mapToGender(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            String gender_ = root.get("gender").textValue();
            return Gender.builder().gender(gender_).build();
        } catch (IOException e) {
            throw new RuntimeException("Error mapping response to nationality", e);
        }
    }

    public static boolean isGenderValid(String userGender, Gender srcGender) {
        if (userGender == null || userGender.trim().isEmpty()) {
            return false;
        }

        if (srcGender == null || srcGender.getGender().trim().isEmpty()) {
            return false;
        }

        return srcGender.getGender().equalsIgnoreCase(userGender);

    }
}
