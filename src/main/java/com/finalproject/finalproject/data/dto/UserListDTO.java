package com.finalproject.finalproject.data.dto;

import com.finalproject.finalproject.data.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserListDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String logoUsername;
    private Role role;
}
