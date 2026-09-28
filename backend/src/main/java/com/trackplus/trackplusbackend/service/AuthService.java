package com.trackplus.trackplusbackend.service;
import com.trackplus.trackplusbackend.exception.InvalidCredentialsException;
import com.trackplus.trackplusbackend.dto.LoginRequest;
import com.trackplus.trackplusbackend.dto.RegisterRequest;
import com.trackplus.trackplusbackend.entity.User;
import com.trackplus.trackplusbackend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtService = jwtService;
    }

    public User register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Always store a BCrypt-hashed password
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // New registered users are drivers
        user.setRole("DRIVER");

        return userRepository.save(user);
    }

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid email or password"
                        )
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        return jwtService.generateToken(user);
    }
}
