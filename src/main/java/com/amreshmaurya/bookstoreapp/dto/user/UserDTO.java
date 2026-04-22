package com.amreshmaurya.bookstoreapp.dto.user;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.amreshmaurya.bookstoreapp.constant.Role;
import com.amreshmaurya.bookstoreapp.constant.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private UUID id;

    private String firstName;
    private String lastName;
    private String username;
    private String email;

    @Builder.Default
    private UserStatus status = UserStatus.INACTIVE;

    private List<Role> roles; 

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}