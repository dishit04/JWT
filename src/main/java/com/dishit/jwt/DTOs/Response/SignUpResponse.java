package com.dishit.jwt.DTOs.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponse {

    private String message;

    private String email;

    private String role;

}
