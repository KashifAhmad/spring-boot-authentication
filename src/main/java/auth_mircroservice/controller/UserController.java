package auth_mircroservice.controller;

import auth_mircroservice.model.ApiResponse;
import auth_mircroservice.model.User;
import auth_mircroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        ApiResponse<User> response = userService.createUser(user);
        HttpStatus status = HttpStatus.valueOf(response.getCode());
        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> loginUser(@RequestBody User user){
        ApiResponse<User> response = userService.login(user.getUsername(), user.getPassword());
        HttpStatus status = HttpStatus.valueOf(response.getCode());
        return new ResponseEntity<>(response, status);
    }
}
