package com.finalproject.finalproject.data.dto;

import com.finalproject.finalproject.data.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotBlank(message = "cannot be blank!")
    private String username;
    private Role role = Role.USER;
    private String logoUsername;
    private String firstName;
    private String lastName;
    private String email;
}
