package com.example.communityoftravellers2.controller;

import com.example.communityoftravellers2.dto.LoginRequest;
import com.example.communityoftravellers2.dto.LoginResponse;
import com.example.communityoftravellers2.dto.SignupDTO;
import com.example.communityoftravellers2.model.User;
import com.example.communityoftravellers2.service.serviceImpl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth/")
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<User> signup(@RequestBody SignupDTO signupDTO){
       return authService.signup(signupDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        return authService.loginRegisteredUser(request);
    }
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        return authService.deleteUser(userId);
    }
    @GetMapping("/get-user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = authService.getUserById(userId);
        return ResponseEntity.ok().body(user);
    }
}
