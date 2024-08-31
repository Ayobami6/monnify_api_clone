package com.ayo.monnify_api_clone.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ServiceException extends RuntimeException {

    private final Integer statusCode;

    public ServiceException(Integer code, String message) {
        super(message);
        this.statusCode = code;
    }

}
