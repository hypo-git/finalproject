package com.finalproject.finalproject.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends BusinessException {
    public AuthException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public static class GenericAuthException extends AuthException {

        public GenericAuthException(String message) {
            super("FFS! What is this error?! :" + message );
        }
    }
}
