package com.dishit.jwt.DTOs.Request;

import lombok.Data;

@Data
public class LogInRequest {

    private String email;

    private String password;
}
