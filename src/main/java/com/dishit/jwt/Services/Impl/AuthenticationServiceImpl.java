package com.dishit.jwt.Services.Impl;

import com.dishit.jwt.DTOs.Request.LogInRequest;
import com.dishit.jwt.DTOs.Request.RefreshTokenRequest;
import com.dishit.jwt.DTOs.Request.SignUpRequest;
import com.dishit.jwt.DTOs.Response.RefreshTokenResponse;
import com.dishit.jwt.DTOs.Response.LogInResponse;
import com.dishit.jwt.DTOs.Response.SignUpResponse;
import com.dishit.jwt.Entities.User;
import com.dishit.jwt.Repositories.UserRepository;
import com.dishit.jwt.Services.AuthenticationService;
import com.dishit.jwt.Services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.AuthenticationManager;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

//    public User signup(SignUpRequest signUpRequest){
//
//        User user = new User();
//
//        user.setEmail(signUpRequest.getEmail());
//        user.setFirstname(signUpRequest.getFirstName());
//        user.setLastname(signUpRequest.getLastName());
//      // user.setRole(Role.USER);
//        user.setRole(signUpRequest.getRole());
//        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
//
//        return userRepository.save(user);
//    }

    public SignUpResponse signup(SignUpRequest signUpRequest) {


        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstname(signUpRequest.getFirstName());
        user.setLastname(signUpRequest.getLastName());
        user.setRole(signUpRequest.getRole());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);

        return new SignUpResponse("User registered successfully", user.getEmail(), user.getRole().toString());
    }


    public LogInResponse login(LogInRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        LogInResponse loginResponse = new LogInResponse();
        loginResponse.setToken(jwt);
        loginResponse.setRefreshToken(refreshToken);

        return loginResponse;
    }


    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);

            RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
            refreshTokenResponse.setNewaccesstoken(jwt);
            refreshTokenResponse.setRefreshToken(refreshTokenRequest.getToken());
            return refreshTokenResponse;
        }

        return null;
    }



}
