package com.example.communityoftravellers2.service.serviceImpl;

import com.example.communityoftravellers2.configuration.JwtService;
import com.example.communityoftravellers2.configuration.PasswordConfig;
import com.example.communityoftravellers2.dto.LoginRequest;
import com.example.communityoftravellers2.dto.LoginResponse;
import com.example.communityoftravellers2.dto.SignupDTO;
import com.example.communityoftravellers2.exception.IncorrectPasswordFormatException;
import com.example.communityoftravellers2.exception.UserAlreadyExistException;
import com.example.communityoftravellers2.exception.UserNotFoundException;
import com.example.communityoftravellers2.model.User;
import com.example.communityoftravellers2.repository.UserRepository;
import com.example.communityoftravellers2.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordConfig passwordConfig;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<User> signup(SignupDTO signupDTO){
        if (!passwordConfig.validatePassword(signupDTO.getPassword())){
            throw new IncorrectPasswordFormatException("incorrect Password format", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByUsername(signupDTO.getUsername())){
            throw new UserAlreadyExistException("Username already exists", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(signupDTO.getUsername());
        user.setPassword(passwordConfig.passwordEncoder().encode(signupDTO.getPassword()));
        user.setRoles(signupDTO.getRoles());

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Override
    public ResponseEntity<LoginResponse> loginRegisteredUser(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

        User user = userRepository.findByUsernameIgnoreCase(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + request.getUsername()));

        String jwtToken = jwtService.generateToken(user);

        LoginResponse response = new LoginResponse();
        response.setToken(jwtToken);
        response.setUser(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Transactional
    @Override
    public ResponseEntity<String> deleteUser(Long userId){
        if(userRepository.findById(userId).isEmpty()){
            throw new UserNotFoundException("user with id : " + userId + " does not exist", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("user successfully deleted");
    }
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId, HttpStatus.NOT_FOUND));
    }
}
