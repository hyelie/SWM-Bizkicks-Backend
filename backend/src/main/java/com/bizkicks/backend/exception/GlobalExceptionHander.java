package com.bizkicks.backend.exception;

import java.io.FileNotFoundException;
import java.security.SignatureException;

import org.hibernate.exception.JDBCConnectionException;
import org.json.JSONObject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
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

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
            log.error("handleHttpMessageNotReadable throws Exceptions : {}", ErrorCode.PARAMETER_NOT_VALID);
            JSONObject json = ErrorResponse.toJson(ErrorCode.PARAMETER_NOT_VALID);
            return new ResponseEntity<Object>(json.toString(), HttpStatus.BAD_REQUEST);
        }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        log.error("handleHttpMediaTypeNotSupported throws Exceptions : {}", ErrorCode.UNSUPPORTED_MEDIA_TYPE);
        JSONObject json = ErrorResponse.toJson(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
        return new ResponseEntity<Object>(json.toString(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        log.error("handleHttpRequestMethodNotSupported throws Exceptions : {}", ErrorCode.METHOD_NOT_ALLOWED);
        JSONObject json = ErrorResponse.toJson(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<Object>(json.toString(), HttpStatus.METHOD_NOT_ALLOWED);

    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(
        MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
            log.error("handleMissingPathVariable throws Exceptions : {}", ErrorCode.MISSING_PARAMETER);
            JSONObject json = ErrorResponse.toJson(ErrorCode.MISSING_PARAMETER);
            return new ResponseEntity<Object>(json.toString(), HttpStatus.BAD_REQUEST);
        }



}
