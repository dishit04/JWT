package com.dishit.jwt.DTOs.Request;


import com.dishit.jwt.Enums.Role;
import lombok.Data;

@Data
public class SignUpRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Role role;
}
