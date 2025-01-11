package com.example.trackyourbills.services;

//import com.example.trackyourbills.dto.UserLoginDTO;
//import com.example.trackyourbills.dto.UserRegisterDTO;
//import com.example.trackyourbills.dto.UserVerifyDTO;
import com.example.trackyourbills.entities.User;
import com.example.trackyourbills.security.auth.AuthenticationRequest;
import com.example.trackyourbills.security.auth.AuthenticationResponse;
import com.example.trackyourbills.security.auth.RegisterRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuthenticationService {
    public AuthenticationResponse register(RegisterRequest request);
    public AuthenticationResponse authenticate(AuthenticationRequest request);
}
