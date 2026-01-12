package com.finalproject.finalproject.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        String path = request.getDescription(false).replace("uri:", "");
        log.warn("Business Exception at {}: {} ", path, ex.getMessage());

        ApiError apiError = new ApiError(
                ex.getHttpStatus(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(apiError, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        String path = request.getDescription(false).replace("uri:", "");
        log.warn("Exception at {}: {} ", path, ex.getMessage());
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Sunucuda beklenmeyen bir hata oluştu: "+ ex.getMessage(),
                request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AccessDeniedException.class, AuthorizationDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");
        log.warn("Security Alert at {}: {} ", path, ex.getMessage());

        ApiError apiError = new ApiError(
                HttpStatus.FORBIDDEN, // Return 403, NOT 500
                "Access Denied: You do not have permission to perform this action.",
                path
        );

        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

}
