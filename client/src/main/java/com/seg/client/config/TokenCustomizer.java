package com.seg.client.config;

import com.seg.client.web.blueprint.BaseWebClient;
import com.seg.client.web.session.TokenWebClient;

import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TokenCustomizer implements WebClientCustomizer{

    private final TokenWebClient tokenWebClient;

    @Override
    public void customize(Builder webClientBuilder) {
        webClientBuilder.filter(extendTokenFilter(tokenWebClient));        
    }
    
    private ExchangeFilterFunction extendTokenFilter(final TokenWebClient clienteWebToken){
        return (clientRequest, next) -> next.exchange(clientRequest)
                .flatMap((clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.UNAUTHORIZED){
                        final String refreshToken = BaseWebClient.getToken().getRefreshToken();
                        return clienteWebToken.extenderToken(refreshToken)
                                    .map(tokenResponse -> {
                                        BaseWebClient.setToken(tokenResponse);
                                        return ClientRequest                                                            
                                                .from(clientRequest)
                                                .headers(headers -> headers.setBearerAuth(tokenResponse.getAccessToken()))
                                                .build();
                                    })
                                    .flatMap(next::exchange);
                    } else {
                        return Mono.just(clientResponse);
                    }
        }));
    }
}
