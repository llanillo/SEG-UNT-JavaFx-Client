package com.seg.precaution.exception;

import com.seg.precaution.response.ErrorResponse;

import org.springframework.http.HttpStatus;

public class ClientException extends ConnectionException {

    public ClientException(final HttpStatus httpStatus, final ErrorResponse errorResponse) {
        super(httpStatus, errorResponse);        
    }
}
