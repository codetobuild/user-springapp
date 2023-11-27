package com.nagarro.userapp.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User {
    String firstname;
    String lastname;
    String name;
    String email;
    String dob;
    int age;
    String gender;
    String nationality;
    String verification_status;
}
