package com.amreshmaurya.bookstoreapp.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amreshmaurya.bookstoreapp.constant.Role;
import com.amreshmaurya.bookstoreapp.constant.UserStatus;
import com.amreshmaurya.bookstoreapp.dao.UserDAO;
import com.amreshmaurya.bookstoreapp.dto.user.CreateUserDTO;
import com.amreshmaurya.bookstoreapp.dto.user.UpdateUserDTO;
import com.amreshmaurya.bookstoreapp.dto.user.UserDTO;
import com.amreshmaurya.bookstoreapp.entity.User;
import com.amreshmaurya.bookstoreapp.mapper.UserMapper;
import com.amreshmaurya.bookstoreapp.util.PasswordUtil;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final UserMapper userMapper;
    private final PasswordUtil passwordUtil;

    public UserService(UserDAO userDAO, UserMapper userMapper, PasswordUtil passwordUtil) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
        this.passwordUtil = passwordUtil;
    }

    @Transactional
    public UserDTO createUser(CreateUserDTO dto) {
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordUtil.hashPassword(dto.getPassword()));
        if (dto.getRoles() != null) {
            user.setRoles(dto.getRoles());
        } else {
            user.setRoles(List.of(Role.USER)); // default role
        }
        User saved = userDAO.save(user);
        return userMapper.toDTO(saved);
    }

    public UserDTO getUser(UUID id) {
        User user = userDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userDAO.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO updateUser(UUID id, UpdateUserDTO dto) {

        User user = userDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateEntity(dto, user);
        if (dto.getPassword() != null) {
            user.setPassword(passwordUtil.hashPassword(dto.getPassword()));
        }

        if (dto.getRoles() != null) {
            user.setRoles(dto.getRoles());
        }

        return userMapper.toDTO(user);
    }

    @Transactional
    public void deleteUser(UUID id) {

        User user = userDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userDAO.delete(user);
    }

    @Transactional
    public UserDTO activateUser(UUID id) {

        User user = userDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(UserStatus.ACTIVE);

        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO updateStatus(UUID id, UserStatus status) {

        User user = userDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(status);

        return userMapper.toDTO(user);
    }

    
}
