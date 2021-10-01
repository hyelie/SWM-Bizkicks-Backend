package com.bizkicks.backend.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum ErrorCode{
    // 400 BAD_REQUEST
    PARAMETER_NOT_VALID(HttpStatus.BAD_REQUEST, "입력 정보가 유효하지 않습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "올바르지 않은 토큰입니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "사용자 정보가 유효하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "사용자 정보가 유효하지 않습니다."),
    MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 path variable이 있습니다."),

    // 401 UNAUTHORIZED
    MEMBER_STATUS_LOGOUT(HttpStatus.UNAUTHORIZED, "사용자가 로그아웃 상태입니다."),
    ID_NOT_EXIST(HttpStatus.UNAUTHORIZED, "아이디가 존재하지 않습니다."),
    PASSWORD_NOT_VALID(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다."),
    NO_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "자격 증명에 실패했습니다."),

    // 403 FORBIDDEN
    NOT_ALLOWED(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다."),

    // 404 NOT_FOUND
    COMPANY_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 법인입니다."),
    KICKBOARD_BRAND_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 킥보드 브랜드입니다."),
    KICKBOARD_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 킥보드입니다."),
    PHONE_NUMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "휴대폰 번호가 존재하지 않습니다."),
    MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다."),
    CONTRACT_NOT_EXIST(HttpStatus.NOT_FOUND, "계약이 존재하지 않습니다."),

    // 405 METHOD NOT ALLOWED
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "존재하지 않는 method입니다."),

    // 409 CONFLICT
    ID_DUPLICATED(HttpStatus.CONFLICT, "아이디가 중복되었습니다."),
    DUPLICATED_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다."),

    // 415 UNSUPPORTED MEDIA TYPE
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "입력 형식이 잘못되었습니다."),

    // 502 BAD GATEWAY
    DATABASE_CONNECTION_ERROR(HttpStatus.BAD_GATEWAY, "서버 데이터베이스 연결에 실패했습니다"),
    REDIS_CONNECTION_ERROR(HttpStatus.BAD_GATEWAY, "서버 메모리 데이터베이스 연결에 실패했습니다"),

    ;

    private final HttpStatus httpStatus;
    private final String msg;


}