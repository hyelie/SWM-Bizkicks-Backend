package com.bizkicks.backend.exception;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String msg;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode){
        return ResponseEntity.status(errorCode.getHttpStatus())
                                .body(ErrorResponse.builder()
                                        .status(errorCode.getHttpStatus().value())
                                        .error(errorCode.getHttpStatus().name())
                                        .code(errorCode.name())
                                        .msg(errorCode.getMsg())
                                        .build()
                                );
    }

    public static JSONObject toJson(ErrorCode errorCode){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("timestamp", LocalDateTime.now());
        jsonObject.put("status", errorCode.getHttpStatus().value());
        jsonObject.put("error", errorCode.getHttpStatus().name());
        jsonObject.put("code", errorCode.name());
        jsonObject.put("msg", errorCode.getMsg());

        return jsonObject;
    }

}
