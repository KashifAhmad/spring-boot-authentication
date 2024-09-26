package com.apprack.auth.controller;

import com.apprack.auth.model.*;
import com.apprack.auth.service.UserService;
import com.apprack.auth.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.apprack.auth.constants.HttpResponseCodes.NOT_FOUND_CODE;
import static com.apprack.auth.constants.HttpResponseCodes.SUCCESS_CODE;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterUser>> createUser(@RequestBody RegisterUser user) {
        ApiResponse<RegisterUser> response = userService.createUser(user);
        HttpStatus status = HttpStatus.valueOf(response.getCode());
        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        ApiResponse<LoginResponse> response = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        HttpStatus status = HttpStatus.valueOf(response.getCode());
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<RegisterUser>> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername(); // Get the username from the authenticated user details
        RegisterUser user = userService.findUserByUsername(username); // Fetch user details from the service

        if (user != null) {
            return ResponseEntity.ok(ApiResponse.success(SUCCESS_CODE, user, "User profile retrieved successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(NOT_FOUND_CODE, "User not found"));
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
        try {
            String username = jwtTokenUtil.extractUsername(token.substring(7)); // Remove 'Bearer ' prefix

            // Check if the user exists in the database
            RegisterUser user = userService.findUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            // Return user details (or a confirmation)
            return ResponseEntity.ok(user);  // Or just send status code OK if you don't want to expose user details
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

}
