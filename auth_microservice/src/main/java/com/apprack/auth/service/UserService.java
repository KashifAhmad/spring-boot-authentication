package com.apprack.auth.service;

import com.apprack.auth.model.ApiResponse;
import com.apprack.auth.model.LoginResponse;
import com.apprack.auth.model.User;
import com.apprack.auth.repository.UserLoginRepository;
import com.apprack.auth.repository.UserRegisterRepository;
import com.apprack.auth.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.apprack.auth.model.RegisterUser;

import static auth_microservice.HttpResponseMessages.*;
import static com.apprack.auth.constants.HttpResponseCodes.*;


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

    public ApiResponse<?> login(String username, String rawPassword) {
        User user = userLoginRepository.findByUsername(username);
        if (user != null) {
            return ApiResponse.error(UNAUTHORIZED_CODE, USERNAME_ALREADY_EXISTS);
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            return ApiResponse.error(UNAUTHORIZED_CODE, INVALID_CREDENTIALS);
        }

        // Generate JWT Token after successful login
        String token = jwtTokenUtil.generateToken(username);

        // Create a response object to return both the user and token
        LoginResponse response = new LoginResponse(token, user);

        return ApiResponse.success(SUCCESS_CODE, response, LOGIN_SUCCESSFULLY);
    }
}