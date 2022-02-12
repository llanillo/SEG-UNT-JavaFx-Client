package com.seg.precaution.response;

import java.util.Map;

import org.springframework.util.CollectionUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ErrorResponse extends Response{

    private Map<String, String> errors; 

    public ErrorResponse(final String code, final String details, final Map<String, String> errors) {        
        super(code, details);
        this.errors = errors;
        if (!CollectionUtils.isEmpty(errors))
            setDetails(errors.values().iterator().next());
    }
}
