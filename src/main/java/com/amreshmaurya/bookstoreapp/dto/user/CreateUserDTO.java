package com.amreshmaurya.bookstoreapp.dto.user;


import java.util.List;

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
public class CreateUserDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

    @Builder.Default
    private UserStatus status = UserStatus.INACTIVE;

    private List<Role> roles;
}
