package com.bizkicks.backend.exception;

import java.security.SignatureException;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;

import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.lettuce.core.RedisConnectionException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHander extends ResponseEntityExceptionHandler {

    // 입력 정보가 다를 때
    // sql에 중복되어 있을 때

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleIntegrityViolateException(){
        log.error("handleDataException throws Exceptions : {}", ErrorCode.PARAMETER_NOT_VALID);
        return ErrorResponse.toResponseEntity(ErrorCode.PARAMETER_NOT_VALID);
    }

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        log.error("custom exception throws Exceptions : {}", e.getErrorCode().getMsg());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = {SignatureException.class})
    protected ResponseEntity<ErrorResponse> handleSignatureException(){
        log.error("SignatureException throws Exceptions : {}", ErrorCode.INVALID_REFRESH_TOKEN);
        return ErrorResponse.toResponseEntity(ErrorCode.INVALID_ACCESS_TOKEN);
    }

    @ExceptionHandler(value = {JDBCConnectionException.class})
    protected ResponseEntity<ErrorResponse> handleDBConnectionException(){
        log.error("handleDBConnectionException throws Exceptions : {}", ErrorCode.DATABASE_CONNECTION_ERROR);
        return ErrorResponse.toResponseEntity(ErrorCode.DATABASE_CONNECTION_ERROR);
    }

    @ExceptionHandler(value = {RedisConnectionException.class})
    protected ResponseEntity<ErrorResponse> handleRedisConnectionException(){
        log.error("handleDBConnectionException throws Exceptions : {}", ErrorCode.REDIS_CONNECTION_ERROR);
        return ErrorResponse.toResponseEntity(ErrorCode.REDIS_CONNECTION_ERROR);
    }

    // @ExceptionHandler(value = {InvalidDefinitionException.class})
    // protected ResponseEntity<ErrorResponse> handleInvalidRequestBody(){
    //     log.error("invalid request body");
    //     return ErrorResponse.toResponseEntity(ErrorCode.PARAMETER_NOT_VALID);
    // }
}
