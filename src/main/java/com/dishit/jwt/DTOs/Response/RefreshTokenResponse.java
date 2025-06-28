package com.dishit.jwt.DTOs.Response;

import lombok.Data;

@Data
public class RefreshTokenResponse {

    private String newaccesstoken;

    private String refreshToken;

}
