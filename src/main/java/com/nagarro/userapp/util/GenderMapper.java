package com.nagarro.userapp.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.userapp.model.Gender;
import com.nagarro.userapp.model.Nationality;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenderMapper {
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
}
