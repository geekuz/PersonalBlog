package org.learning.personalblog.service;

import lombok.RequiredArgsConstructor;
import org.learning.personalblog.dto.JwtAuthResponse;
import org.learning.personalblog.dto.LoginDto;
import org.learning.personalblog.dto.RegisterDto;
import org.learning.personalblog.entity.User;
import org.learning.personalblog.repository.UserRepository;
import org.learning.personalblog.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        return new JwtAuthResponse(token);
    }

    public User register(RegisterDto registerDto) {
        // ... existing register code ...
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new IllegalArgumentException("Email is already in use!");
        }

        User newUser = new User();
        newUser.setUsername(registerDto.getUsername());
        newUser.setEmail(registerDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        newUser.setRole(registerDto.getRole());

        return userRepository.save(newUser);
    }
}