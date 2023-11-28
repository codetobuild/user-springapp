package com.nagarro.userapp.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.userapp.entities.Users;
import com.nagarro.userapp.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static List<User> mapToUser(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode resultsNode = root.path("results");

            if (resultsNode.isArray() && resultsNode.size() > 0) {
                List<User> userList = new ArrayList<User>();
                for( JsonNode userNode :  resultsNode){
                    userList.add(buildUserFromJsonNode(userNode));
                }
                return userList;
            }
            throw new RuntimeException("No user data found in the response.");

        } catch (IOException e) {
            throw new RuntimeException("Error mapping response to User", e);
        }
    }

    private static User buildUserFromJsonNode(JsonNode userNode) {
        String first = userNode.path("name").get("first").textValue();
        String last = userNode.path("name").get("last").textValue();
        String name = first + " " + last;
        String email = userNode.path("email").textValue();
        String dob = userNode.path("dob").path("date").textValue();
        int age = userNode.path("dob").path("age").intValue();
        String gender = userNode.path("gender").textValue();
        String nationality = userNode.path("nat").textValue();
        String verificationStatus = "TO_BE_VERIFIED";

        return User.builder()
                .firstname(first)
                .lastname(last)
                .name(name)
                .email(email)
                .dob(dob)
                .age(age)
                .gender(gender)
                .nationality(nationality)
                .verification_status(verificationStatus)
                .build();
    }

    public static List<Users> mapToUsersList(List<User> userList) {
        return userList.stream()
                .map(UserMapper::mapToUsersEntity)
                .collect(Collectors.toList());
    }

    public static Users mapToUsersEntity(User user) {

        Users users = new Users();
        users.setName(user.getName());
        users.setEmail(user.getEmail());
        users.setDob(user.getDob());
        users.setAge(user.getAge());
        users.setGender(user.getGender());
        users.setNationality(user.getNationality());
        users.setVerification_status(user.getVerification_status());
        return users;
    }
}
