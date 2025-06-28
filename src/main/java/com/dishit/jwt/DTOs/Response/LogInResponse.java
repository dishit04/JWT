package com.dishit.jwt.DTOs.Response;

import lombok.Data;

@Data
public class LogInResponse {

    private String token;

    private String refreshToken;
}
