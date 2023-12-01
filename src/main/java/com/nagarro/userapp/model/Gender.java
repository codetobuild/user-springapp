package com.nagarro.userapp.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Gender {
    String gender;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gender gender1 = (Gender) o;
        return gender.equals(gender1.gender);
    }

    @Override
    public int hashCode() {
        return gender.hashCode();
    }
}
