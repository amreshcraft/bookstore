package com.amreshmaurya.bookstoreapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amreshmaurya.bookstoreapp.dto.user.CreateUserDTO;
import com.amreshmaurya.bookstoreapp.dto.user.UpdateUserDTO;
import com.amreshmaurya.bookstoreapp.dto.user.UserDTO;
import com.amreshmaurya.bookstoreapp.service.UserService;
import com.amreshmaurya.bookstoreapp.wrapper.ApiResponse;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserDTO> createUser(
            @Valid @RequestBody CreateUserDTO dto){
        return ApiResponse.success(userService.createUser(dto));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDTO> getUserById(@PathVariable UUID id){
        return ApiResponse.success(userService.getUser(id));
    }

    @GetMapping
    public ApiResponse<List<UserDTO>> getAllUsers(){
        return ApiResponse.success(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ApiResponse<UserDTO> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserDTO dto){
        return ApiResponse.success(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return ApiResponse.success(null);
    }
}
