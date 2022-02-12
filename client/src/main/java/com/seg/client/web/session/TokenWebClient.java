package com.seg.client.web.session;


import com.seg.client.web.blueprint.ExceptionWebClientHandle;
import com.seg.domain.jwt.TokenResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class TokenWebClient implements ExceptionWebClientHandle{
    
    @Value("${cliente.session.refresh_auth.url}")
    private String REFRESH_TOKEN_URL;   
    
    private final WebClient webClient;

    @Autowired
    public TokenWebClient(@Lazy WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<TokenResponse> extenderToken(final String refreshToken){
        return webClient.get()
                .uri(REFRESH_TOKEN_URL)        
                .headers(headers -> headers.setBearerAuth(refreshToken))        
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .onErrorMap(handleExceptions());
    }
}
