package com.apprack.auth.controller;

import com.apprack.auth.model.*;
import com.apprack.auth.service.UserService;
import com.apprack.auth.utils.JwtTokenUtil;
import com.apprack.auth.utils.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static auth_microservice.HttpResponseMessages.INVALID_CREDENTIALS;
import static auth_microservice.HttpResponseMessages.LOGIN_SUCCESSFULLY;
import static com.apprack.auth.constants.HttpResponseCodes.SUCCESS_CODE;
import static com.apprack.auth.constants.HttpResponseCodes.UNAUTHORIZED_CODE;
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterUser>> createUser(@RequestBody RegisterUser user) {
        ApiResponse<RegisterUser> response = userService.createUser(user);
        HttpStatus status = HttpStatus.valueOf(response.getCode());
        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(token);
    }

}
