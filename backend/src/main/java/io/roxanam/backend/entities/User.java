package io.roxanam.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean isActive;
}
