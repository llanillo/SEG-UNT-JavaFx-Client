package com.seg.precaution.exception;

import com.seg.precaution.response.ErrorResponse;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ConnectionException extends RuntimeException{
    
    private final HttpStatus httpStatus;
    private final ErrorResponse errorResponse;

    public ConnectionException(final HttpStatus httpStatus, final ErrorResponse errorResponse) {
        super(httpStatus.getReasonPhrase());
        this.httpStatus = httpStatus;
        this.errorResponse = errorResponse;
    }
        
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public String getMessage() {
        return errorResponse.getDetails();
    }
}
