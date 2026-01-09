package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.AuthResponse;
import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.entity.User;
import com.example.exception.UserAlreadyExistsException;
import com.example.repository.UserRepository;
import com.example.security.JwtUtil;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    public void register(RegisterRequest request) {
        if (userRepo.existsByUsername(request.username)) {
            throw new UserAlreadyExistsException();
        }

        User user = new User();
        user.setUsername(request.username);
        user.setPassword(encoder.encode(request.password));

        userRepo.save(user);
    }

    public String login(LoginRequest request) {

        User user = userRepo.findByUsername(request.username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }

}
