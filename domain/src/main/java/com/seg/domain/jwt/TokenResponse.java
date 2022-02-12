package com.seg.domain.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponse {
    
    private String accessToken;
    private String refreshToken;
    private String type;
}
