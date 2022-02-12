package com.seg.client.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import reactor.core.publisher.Mono;

@Component
public class LogCustomizer implements WebClientCustomizer{

    private static final Logger log = LoggerFactory.getLogger(LogCustomizer.class);

    @Override
    public void customize(final Builder webClientBuilder) {
        webClientBuilder.filter(loggerResponseFilter());
        webClientBuilder.filter(loggerRequestFilter());
    }
    
    private ExchangeFilterFunction loggerRequestFilter(){
        return (clientRequest, next) -> {
            log.info("Request Status: {} {}", clientRequest.method(), clientRequest.url());
            log.info("---- Request Header ----");
            clientRequest.headers().forEach(this::loggerHeader);
            return next.exchange(clientRequest);
        };
    }

    private ExchangeFilterFunction loggerResponseFilter(){
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("Response Status: {}", clientResponse.statusCode());
            log.info("---- Response Header ----");
            clientResponse.headers().asHttpHeaders().forEach(this::loggerHeader);
            return Mono.just(clientResponse);
        });
    }    

    private void loggerHeader(final String name, final List<String> values){
        values.forEach(value -> log.info("{} = {}", name, values));
    }
}
