package com.bizkicks.backend.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHander extends ResponseEntityExceptionHandler {

    // 입력 정보가 다를 때
    // sql에 중복되어 있을 때

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleIntegrityViolateException(){
        log.error("handleDataException thor Exceptions : {}", ErrorCode.PARAMETER_NOT_VALID);
        return ErrorResponse.toResponseEntity(ErrorCode.PARAMETER_NOT_VALID);
    }

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        log.error("handleDataException thor Exceptions : {}", e.getErrorCode());;
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    
}
