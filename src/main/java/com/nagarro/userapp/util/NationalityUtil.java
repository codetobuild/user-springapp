package com.nagarro.userapp.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.userapp.model.Nationality;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NationalityUtil {
    public static List<Nationality> mapToNationality(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode resultsNode = root.path("country");
            List<Nationality> nationalityList = new ArrayList<Nationality>();

            if (resultsNode.isArray() && resultsNode.size() > 0) {
                for (JsonNode countryNode : resultsNode) {
                    nationalityList.add(
                            new Nationality(countryNode.get("country_id").textValue())
                    );
                }
            }
            return nationalityList;

        } catch (IOException e) {
            throw new RuntimeException("Error mapping response to nationality", e);
        }
    }


    public static boolean isUserNationalityValid(String userNationality, List<Nationality> nationalityList) {
        if (userNationality == null || userNationality.trim().isEmpty()) {
            return false; // User input is null or empty
        }

        if (nationalityList == null || nationalityList.isEmpty()) {
            return false; // Nationality list is null or empty
        }

        String normalizedInput = userNationality.trim().toLowerCase();

        return nationalityList.stream()
                .anyMatch(nationality ->
                        nationality != null && nationality.getNational().equalsIgnoreCase(normalizedInput));
    }
}
