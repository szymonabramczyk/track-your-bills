package com.example.trackyourbills.services.impl;

//import com.example.trackyourbills.dto.UserLoginDTO;
//import com.example.trackyourbills.dto.UserRegisterDTO;
//import com.example.trackyourbills.dto.UserVerifyDTO;
import com.example.trackyourbills.entities.User;
import com.example.trackyourbills.repositories.UserRepository;
import com.example.trackyourbills.security.auth.AuthenticationRequest;
import com.example.trackyourbills.security.auth.AuthenticationResponse;
import com.example.trackyourbills.security.auth.RegisterRequest;
import com.example.trackyourbills.security.user.Role;
import com.example.trackyourbills.services.AuthenticationService;
//import com.example.trackyourbills.services.EmailService;
import com.example.trackyourbills.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        Optional<User> found_user = userRepository.findByEmail(user.getEmail());
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("uid", found_user.get().getId());
        var jwtToken = jwtService.generateToken(extraClaims, user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        ));

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("uid", user.getId());
        var jwtToken = jwtService.generateToken(extraClaims, user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}