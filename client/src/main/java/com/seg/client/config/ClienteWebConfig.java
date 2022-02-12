package com.seg.client.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class ClienteWebConfig {    

    @Value("${client.base.url}")
    private String BASE_URL;    

    @Bean    
    public WebClient webClient(WebClient.Builder webClientBuilder){
        final long DELAY_TIME = 10;
        final HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) (DELAY_TIME * 1000))
                .doOnConnected(conexion -> {
                    conexion
                        .addHandler(new ReadTimeoutHandler(DELAY_TIME, TimeUnit.SECONDS))
                        .addHandler(new WriteTimeoutHandler(DELAY_TIME, TimeUnit.SECONDS));
                });

        return webClientBuilder
                .baseUrl(BASE_URL)                
                .clientConnector(new ReactorClientHttpConnector(httpClient))                                
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)                                                                
                .build();
    }
}
