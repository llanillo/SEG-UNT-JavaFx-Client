package com.seg.domain.jwt;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class LoginRequest {
    
    private final String dni;
    private final char[] password;
    
    public void deletePassword(){
        Arrays.fill(this.password, '*');
    }
}
