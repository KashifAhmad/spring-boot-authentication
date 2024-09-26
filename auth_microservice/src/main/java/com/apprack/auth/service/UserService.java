package com.apprack.auth.service;

import com.apprack.auth.model.*;
import com.apprack.auth.repository.UserLoginRepository;
import com.apprack.auth.repository.UserRegisterRepository;
import com.apprack.auth.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static auth_microservice.HttpResponseMessages.*;
import static com.apprack.auth.constants.HttpResponseCodes.*;


import java.util.ArrayList;
import java.util.Optional;
@Service
public class UserService {

    @Autowired
    private UserRegisterRepository userRegisterRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // Method to create a user
    public ApiResponse<RegisterUser> createUser(RegisterUser user) {
        Optional<RegisterUser> existingUser = userRegisterRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ApiResponse.error(UNAUTHORIZED_CODE, USERNAME_ALREADY_EXISTS);
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        RegisterUser savedUser = userRegisterRepository.save(user);
        return ApiResponse.success(SUCCESS_CODE, savedUser, SUCCESS_MESSAGE);
    }

    // Method to login a user
    public ApiResponse<LoginResponse> login(String username, String rawPassword) {
        User user = userLoginRepository.findByUsername(username);
        if (user == null) {
            return ApiResponse.error(UNAUTHORIZED_CODE, USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            return ApiResponse.error(UNAUTHORIZED_CODE, INVALID_CREDENTIALS);
        }

        // Generate JWT Token after successful login
        String token = jwtTokenUtil.generateToken(user.getUsername());

        // Create a LoginResponse object to return both the user and token
        LoginResponse response = new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getCreatedTimeStamp(),
                token,
                "Login successful"
        );

        return ApiResponse.success(SUCCESS_CODE, response, LOGIN_SUCCESSFULLY);
    }

    // Method to load user details by username
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to load user with username: " + username); // Log the attempt
        RegisterUser user = userRegisterRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public RegisterUser findUserByUsername(String username) {
        return userRegisterRepository.findByUsername(username)
                .orElse(null); // Return null if user is not found
    }

}
