package com.bizkicks.backend.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHander extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleConstraintViolateException(){
        log.error("handleDataException thor Exceptions : {}", ErrorCode.PARAMETER_NOT_VALID);
        return ErrorResponse.toResponseEntity(ErrorCode.PARAMETER_NOT_VALID);
    }

    @ExceptionHandler(value = {DuplicateKeyException.class})
    protected ResponseEntity<ErrorResponse> handleDuplicateException(){
        log.error("handleDataException thor Exceptions : {}", ErrorCode.DUPLICATED_RESOURCE);
        return ErrorResponse.toResponseEntity(ErrorCode.DUPLICATED_RESOURCE);
    }


    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        log.error("handleDataException thor Exceptions : {}", e.getErrorCode());;
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    
}
