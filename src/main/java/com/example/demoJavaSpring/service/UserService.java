package com.example.demoJavaSpring.service;

import com.example.demoJavaSpring.model.ApiResponse;
import com.example.demoJavaSpring.model.User;
import com.example.demoJavaSpring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.demoJavaSpring.constants.HttpResponseCodes.*;
import static com.example.demoJavaSpring.constants.HttpResponseMessages.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiResponse<User> createUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            return ApiResponse.error(UNAUTHORIZED_CODE, USERNAME_ALREADY_EXISTS);
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        User saveUser = userRepository.save(user);
        return ApiResponse.success(SUCCESS_CODE, saveUser, SUCCESS_MESSAGE);
    }

    public ApiResponse<User> login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ApiResponse.error(NOT_FOUND_CODE, USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            return ApiResponse.error(UNAUTHORIZED_CODE, INVALID_CREDENTIALS);
        }
        return ApiResponse.success(SUCCESS_CODE, user, LOGIN_SUCCESSFULLY);
    }
}