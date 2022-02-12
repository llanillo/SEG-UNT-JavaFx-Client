package com.seg.client.web.user;

import java.util.List;

import com.seg.client.web.blueprint.BaseWebClient;
import com.seg.domain.user.dto.UserBasic;
import com.seg.domain.user.fx.UserResponseFx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserWebClient extends BaseWebClient{

    public UserWebClient(WebClient webClient) {
        super(webClient);        
    }
    
    @Value("${cliente.user.find_by_id.url}")
    private String FIND_BY_ID_ULR;
    @Value("${cliente.user.find_all_basic.url}")
    private String FIND_ALL_BASIC_URL;
    
    public UserResponseFx findById(final Long id){
        return webClient.get()
                    .uri(FIND_BY_ID_ULR + "/{id}", id)
                    .headers(header -> header.setBearerAuth(getToken().getAccessToken()))
                    .retrieve()
                    .bodyToMono(UserResponseFx.class)                    
                    .onErrorMap(handleExceptions())       
                    .block();                
    }

    public List<UserBasic> findAllBasics(){
        return webClient.get()
                    .uri(FIND_ALL_BASIC_URL)
                    .headers(header -> header.setBearerAuth(getToken().getAccessToken()))
                    .retrieve()
                    .bodyToFlux(UserBasic.class)
                    .collectList()
                    .onErrorMap(handleExceptions())
                    .block();
    }
}
