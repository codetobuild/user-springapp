package com.nagarro.userapp.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;
    private String dob;
    private Integer age;
    private String gender;
    private String nationality;
    private String verification_status;
    private LocalDateTime date_created;
    private LocalDateTime date_modified;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.date_created = now;
        this.date_modified = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.date_modified = LocalDateTime.now();
    }

}
