package com.amreshmaurya.bookstoreapp.mapper;

import org.springframework.stereotype.Component;

import com.amreshmaurya.bookstoreapp.dto.user.CreateUserDTO;
import com.amreshmaurya.bookstoreapp.dto.user.UpdateUserDTO;
import com.amreshmaurya.bookstoreapp.dto.user.UserDTO;
import com.amreshmaurya.bookstoreapp.entity.User;

@Component
public class UserMapper {

    public User toEntity(CreateUserDTO dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .status(dto.getStatus())
                .build();
    }

    public void updateEntity(UpdateUserDTO dto, User user) {

        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }
        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
        if (dto.getStatus() != null) {
            user.setStatus(dto.getStatus());
        }

    }

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .roles(user.getRoles())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}