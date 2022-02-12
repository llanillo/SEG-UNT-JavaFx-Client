package com.seg.precaution.exception;

import com.seg.precaution.response.ErrorResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends RuntimeException{

    private ErrorResponse errorResponse;

    public ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }
}
