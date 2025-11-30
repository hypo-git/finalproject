package com.finalproject.finalproject.exception;

import org.springframework.http.HttpStatus;

public class EntityDefinitionException extends BusinessException {
    public EntityDefinitionException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    //Not found exception
    public static class EntityDefinitionNotFoundException extends EntityDefinitionException {
        public EntityDefinitionNotFoundException(String entityCode) {
            super("Bu kod ile bir entity bulunamadı! Entity code:" + entityCode );
        }
        public EntityDefinitionNotFoundException(Long id) {
            super("Bu id ile bir entity bulunamadı! Entity id:" + id );
        }

        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.NOT_FOUND;
        }
    }

    //Already exists exception
    public static class  EntityDefinitionAlreadyExistsException extends EntityDefinitionException {
        public EntityDefinitionAlreadyExistsException(String entityCode) {
            super("Bu kod ile bir entity zaten var! Entity code:" + entityCode );
        }

        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.CONFLICT;
        }
    }

}
