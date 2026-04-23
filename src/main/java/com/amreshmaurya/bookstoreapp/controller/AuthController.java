package com.amreshmaurya.bookstoreapp.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amreshmaurya.bookstoreapp.dto.user.CreateUserDTO;
import com.amreshmaurya.bookstoreapp.dto.user.LoginDTO;
import com.amreshmaurya.bookstoreapp.dto.user.UserDTO;
import com.amreshmaurya.bookstoreapp.entity.User;
import com.amreshmaurya.bookstoreapp.service.UserService;
import com.amreshmaurya.bookstoreapp.util.JwtUtil;
import com.amreshmaurya.bookstoreapp.util.PasswordUtil;
import com.amreshmaurya.bookstoreapp.wrapper.ApiResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final PasswordUtil passwordUtil;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    AuthController(PasswordUtil passwordUtil, UserService userService, JwtUtil jwtUtil) {
        this.passwordUtil = passwordUtil;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginDTO dto) {

        User user = userService.findByEmail(dto.getEmail());

        if (!passwordUtil.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtUtil.generateToken(user.getId().toString());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId().toString());

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .path("/")
                .maxAge(15 * 60)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .build();

        Map<String, String> data = new HashMap<>();
        data.put("accessToken", accessToken);
        data.put("userId", user.getId().toString());

        return ResponseEntity.ok()
                .header("Set-Cookie", accessCookie.toString())
                .header("Set-Cookie", refreshCookie.toString())
                .body(ApiResponse.success(data));
    }

    // signup
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserDTO> createUser(
            @Valid @RequestBody CreateUserDTO dto) {
        return ApiResponse.success(userService.createUser(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<?>> refresh(HttpServletRequest request) {

        String refreshToken = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new RuntimeException("No refresh token"));

        if (!jwtUtil.isRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String userId = jwtUtil.extractUsername(refreshToken);

        String newAccessToken = jwtUtil.generateToken(userId);

        ResponseCookie cookie = ResponseCookie.from("accessToken", newAccessToken)
                .httpOnly(true)
                .path("/")
                .maxAge(15 * 60)
                .build();

        ApiResponse<String> response = ApiResponse.success("Token refreshed successfully");

        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .body(response);
    }
}
