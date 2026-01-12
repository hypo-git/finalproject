package com.finalproject.finalproject.data.dto;

import com.finalproject.finalproject.data.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Should be minimum 6 characters long.")
    private String password;

    private Role role = Role.USER;

    private String logoUserName;
    @NotBlank(message = "Adı gerekli")
    private String firstName;
    @NotBlank(message = "Soyadı Gerekli")
    private String lastName;


}
