package com.example.communityoftravellers2.service;

import com.example.communityoftravellers2.dto.LoginRequest;
import com.example.communityoftravellers2.dto.LoginResponse;
import com.example.communityoftravellers2.dto.SignupDTO;
import com.example.communityoftravellers2.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {
    ResponseEntity<User> signup(SignupDTO signupDTO);

    ResponseEntity<LoginResponse> loginRegisteredUser(LoginRequest request);

    @Transactional
    ResponseEntity<String> deleteUser(Long userId);

    User getUserById(Long userId);
}
