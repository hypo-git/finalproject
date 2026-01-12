package com.finalproject.finalproject.controller;

import com.finalproject.finalproject.data.dto.*;
import com.finalproject.finalproject.data.model.User;
import com.finalproject.finalproject.data.model.UserSetting;
import com.finalproject.finalproject.service.UserManagementService;
import com.finalproject.finalproject.service.UserSettingService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.finalproject.finalproject.service.AuthService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserManagementService userManagementService;
    private final UserSettingService userSettingService;

    @Value("${jwt.expiration}")
    private Integer jwtExpirationInSeconds;
    @Value("${cookie.secure}")
    private boolean cookieSecure;

    @PostMapping("/createUser")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<AuthResponse> createUser(
            @Valid @RequestBody RegisterRequest request,
            HttpServletResponse response){

        AuthResponse authResponse = authService.register(request);

        //Cookie tokenCookie = createCookie("token", authResponse.getToken());
        //response.addCookie(tokenCookie);
        authResponse.setToken(null);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response){

        AuthResponse authResponse = authService.login(request);

        Cookie tokenCookie = createCookie("token", authResponse.getToken());
        response.addCookie(tokenCookie);
        authResponse.setToken(null);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response){
        Cookie tokenCookie = new Cookie("token", null);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(false);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(0);
        response.addCookie(tokenCookie);
        return ResponseEntity.ok().body(Map.of("message", "You have been logged out"));
    }

    @PutMapping("/{userName}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<User> updateUser(@Valid @PathVariable String userName, @RequestBody UserUpdateRequest user){
        return ResponseEntity.ok(userManagementService.updateUser(userName, user));
    }

    @DeleteMapping("/delete/{userName}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<?> deleteUser(@PathVariable String userName){
        userManagementService.deleteUserByUsername(userName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<List<UserListDTO>> getAllUsers(){
        return  ResponseEntity.ok(userManagementService.getAllUserList());
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser() {
        var authentication = org.springframework.security.core.context
                .SecurityContextHolder
                .getContext()
                .getAuthentication();

        User user = userManagementService.getUserByUsername(authentication.getName());
        UserSetting userSetting = userSettingService.getOrCreateUserSetting(user);

        return ResponseEntity.ok(
                AuthResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .logoUsername(user.getLogoUsername())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .settings(
                                UserSettingDTO.builder()
                                        .colorScheme(userSetting.getColorScheme())
                                        .fontSize(userSetting.getFontSize())
                                        .primaryColor(userSetting.getPrimaryColor())
                                        .build()
                        )
                        .build()
        );
    }

    @PutMapping("/settings")
    public ResponseEntity<UserSettingDTO> updateSettings(@RequestBody UserSettingDTO userSettingDTO){
        var authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        User user = userManagementService.getUserByUsername(authentication.getName());

        UserSettingDTO updated = userSettingService.updateUserSettingDTO(user, userSettingDTO);

        return ResponseEntity.ok(updated);
    }



    public Cookie createCookie(String cookieName, String cookieValue){
        Cookie tokenCookie = new Cookie(cookieName, cookieValue);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(cookieSecure);
        tokenCookie.setMaxAge(jwtExpirationInSeconds);
        tokenCookie.setPath("/");
        return tokenCookie;
    }

}
