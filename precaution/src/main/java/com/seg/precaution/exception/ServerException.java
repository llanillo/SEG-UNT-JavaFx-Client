package com.seg.precaution.exception;

import com.seg.precaution.response.ErrorResponse;

import org.springframework.http.HttpStatus;

public class ServerException extends ConnectionException{

    public ServerException(final HttpStatus httpStatus, final ErrorResponse errorResponse) {
        super(httpStatus, errorResponse);        
    }
    
}
