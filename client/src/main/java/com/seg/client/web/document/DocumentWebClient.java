package com.seg.client.web.document;

import java.util.List;

import com.seg.client.web.blueprint.BaseWebClient;
import com.seg.domain.document.dto.DocumentManage;
import com.seg.domain.document.dto.DocumentProperties;
import com.seg.domain.document.dto.DocumentResponse;
import com.seg.domain.enumeration.Role;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DocumentWebClient extends BaseWebClient{

    @Value("${client.document.upload.url}")
    private String UPLOAD_URL;
    @Value("${client.document.find_by_id.url}")
    private String FIND_BY_ID_URL;
    @Value("${client.document.find_by_role.url}")
    private String FIND_BY_ROLE_URL;
    @Value("${client.document.find_all_pending.url}")
    private String FIND_ALL_PENDING;

    public DocumentWebClient(WebClient webClient) {
        super(webClient);        
    }

    public DocumentResponse uploadDocument(final DocumentProperties documentProperties){
        return webClient.post()
                .uri(UPLOAD_URL)
                .headers(headers -> headers.setBearerAuth(getToken().getAccessToken()))
                .body(BodyInserters.fromValue(documentProperties))
                .retrieve()
                .bodyToMono(DocumentResponse.class)                                
                .onErrorMap(handleExceptions())
                .block();
    }
    
    public DocumentResponse findById(final Long id){
        return webClient.get()
                .uri(FIND_BY_ID_URL + "/{id}", id)
                .headers(header -> header.setBearerAuth(getToken().getAccessToken()))
                .retrieve()
                .bodyToMono(DocumentResponse.class)
                .onErrorMap(handleExceptions())
                .block();
    }

    public List<DocumentResponse> findByRole(final Role role){
        return webClient.post()
                .uri(FIND_BY_ROLE_URL)    
                .headers(headers -> headers.setBearerAuth(getToken().getAccessToken()))
                .body(BodyInserters.fromValue(role))
                .retrieve()              
                .bodyToFlux(DocumentResponse.class)                
                .collectList()
                .onErrorMap(handleExceptions())
                .block();
    }

    public List<DocumentManage> findAllPendingForApproval(){
        return webClient.get()
                .headers(headers -> headers.setBearerAuth(getToken().getAccessToken()))
                .retrieve()
                .bodyToFlux(DocumentManage.class)
                .collectList()
                .onErrorMap(handleExceptions())
                .block();
    }
}
