package com.seg.client.config;

import com.seg.precaution.exception.ClientException;
import com.seg.precaution.exception.ServerException;
import com.seg.precaution.response.ErrorResponse;

import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import reactor.core.publisher.Mono;

@Component
public class ExcepcionCustomizer implements WebClientCustomizer{	
    
    @Override
    public void customize(Builder webClientBuilder) {
        webClientBuilder.filter(exceptionFilter());
    }
    
    private ExchangeFilterFunction exceptionFilter(){
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode() != null && clientResponse.statusCode().isError()){
                if (clientResponse.statusCode().is5xxServerError()){
                    return clientResponse.bodyToMono(ErrorResponse.class)
                        .flatMap((error) -> {                            
                            return Mono.error(new ServerException(clientResponse.statusCode(), error));
                        });
                }
                else if (clientResponse.statusCode().is4xxClientError()){
                    return clientResponse.bodyToMono(ErrorResponse.class)
                        .flatMap((error) -> {                                    
                            return Mono.error(new ClientException(clientResponse.statusCode(), error));                    
                        });
                }
            }            
            
            return Mono.just(clientResponse);
        });
    }
}
