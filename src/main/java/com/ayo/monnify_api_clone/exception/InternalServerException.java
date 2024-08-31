package com.ayo.monnify_api_clone.exception;


public class InternalServerException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Don't fret this is from our end";

    public InternalServerException() {
        super(DEFAULT_MESSAGE);
    }
    public InternalServerException(String message) {
        super(message);
    }

}
