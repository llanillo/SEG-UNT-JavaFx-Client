package com.seg.client.web.session;

import com.seg.client.web.blueprint.BaseWebClient;
import com.seg.domain.jwt.JwtResponse;
import com.seg.domain.user.dto.UserProperties;
import com.seg.domain.user.fx.UserResponseFx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public final class SessionWebClient extends BaseWebClient{

    @Value("${cliente.session.login.url}")
    private String LOG_IN_URL;
    @Value("${cliente.session.register.url}")
    private String REGISTER_URL; 

    public SessionWebClient(final WebClient webClient) {
        super(webClient);        
    }
    
    public JwtResponse login(final String dni, final String password){
        return webClient.post()
                    .uri(LOG_IN_URL)
                    .headers(header -> header.setBasicAuth(dni, password))                                                                     
                    .retrieve()
                    .bodyToMono(JwtResponse.class)
                    .onErrorMap(handleExceptions())
                    .block();
    }
    
    public UserResponseFx register(final UserProperties userProperties){
        return webClient.post()
                    .uri(REGISTER_URL)                    
                    .body(BodyInserters.fromValue(userProperties))
                    .retrieve()
                    .bodyToMono(UserResponseFx.class)                    
                    .onErrorMap(handleExceptions())
                    .block();
    }
}
