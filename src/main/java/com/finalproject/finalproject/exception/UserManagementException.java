package com.finalproject.finalproject.exception;

import org.springframework.http.HttpStatus;

public class UserManagementException extends BusinessException {
    public UserManagementException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    //Not found exception
    public static class UserNotFoundException extends UserManagementException {
        public UserNotFoundException(String username) {
            super("Bu kullanıcı kodunda bir kullanıcı bulunamadı: " + username );
        }

        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.NOT_FOUND;
        }
    }
}
