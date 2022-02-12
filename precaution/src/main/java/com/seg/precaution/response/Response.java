package com.seg.precaution.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Response {
    
    private String code;    
    private String details;
    
    public Response(final String code) {
        this.code = code;        
    }

    public Response(final String codigo, final String details) {
        this.code = codigo;        
        this.details = details;
    }
}
