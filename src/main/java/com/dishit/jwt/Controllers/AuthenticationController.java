package com.dishit.jwt.Controllers;

import com.dishit.jwt.DTOs.Request.LogInRequest;
import com.dishit.jwt.DTOs.Request.RefreshTokenRequest;
import com.dishit.jwt.DTOs.Request.SignUpRequest;
import com.dishit.jwt.DTOs.Response.LogInResponse;
import com.dishit.jwt.DTOs.Response.RefreshTokenResponse;
import com.dishit.jwt.DTOs.Response.SignUpResponse;
import com.dishit.jwt.Entities.User;
import com.dishit.jwt.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/app/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponse> login(@RequestBody LogInRequest loginRequest){
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> login(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

}




