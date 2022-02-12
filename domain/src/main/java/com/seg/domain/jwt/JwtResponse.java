package com.seg.domain.jwt;

import com.seg.domain.user.dto.UserResponse;

import lombok.Data;

@Data
public class JwtResponse {

    private String accessToken;
    private String refreshToken;
    private String type;
    
    private UserResponse user;
}
