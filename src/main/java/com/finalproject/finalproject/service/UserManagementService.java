package com.finalproject.finalproject.service;

import com.finalproject.finalproject.data.dto.AuthResponse;
import com.finalproject.finalproject.data.dto.UserListDTO;
import com.finalproject.finalproject.data.dto.UserUpdateRequest;
import com.finalproject.finalproject.data.mapper.UserMapper;
import com.finalproject.finalproject.data.model.User;
import com.finalproject.finalproject.exception.UserManagementException;
import com.finalproject.finalproject.repository.UserRepository;
import com.finalproject.finalproject.security.JwtUtil;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class UserManagementService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public User updateUser(String userName, UserUpdateRequest request){
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("Username does not exists: " + userName));

        user.setLogoUsername(request.getLogoUsername());
        user.setRole(request.getRole());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }

    public User getUserByUsername(String userName){
        return  userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("Username does not exists"));
    }

    public void deleteUserByUsername(String userName){
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if(currentUser.equals(userName)){
            throw new UserManagementException("Kullanıcı kendi hesabını silemez!");
        }

        User deletedUser = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserManagementException.UserNotFoundException(userName));
        userRepository.delete(deletedUser);
    }
/*
    public List<AuthResponse> getAllUser(){
        return userMapper.toAuthResponseList(userRepository.findAll());
    }
*/
    public List<UserListDTO> getAllUserList(){
        return userRepository.findAll().stream()
                .map(user -> UserListDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .logoUsername(user.getLogoUsername())
                        .role(user.getRole())
                        .build()
                ).collect(Collectors.toList());
    }

}
