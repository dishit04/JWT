package com.dishit.jwt.Services;

import com.dishit.jwt.DTOs.Request.LogInRequest;
import com.dishit.jwt.DTOs.Request.RefreshTokenRequest;
import com.dishit.jwt.DTOs.Request.SignUpRequest;
import com.dishit.jwt.DTOs.Response.LogInResponse;
import com.dishit.jwt.DTOs.Response.RefreshTokenResponse;
import com.dishit.jwt.DTOs.Response.SignUpResponse;
import com.dishit.jwt.Entities.User;

public interface AuthenticationService {

    SignUpResponse signup(SignUpRequest signUpRequest);

    LogInResponse login(LogInRequest loginRequest);

    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
