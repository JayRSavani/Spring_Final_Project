package com.humber.final_project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationForm {
    private String userId;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String name;
}
