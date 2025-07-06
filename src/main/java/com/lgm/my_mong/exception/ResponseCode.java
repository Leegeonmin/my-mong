package com.lgm.my_mong.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode implements CommonResponseCode {


    METHOD_ARG_NOT_VALID("0001", "유효성 오류가 발생했습니다."),
    COMMON_INVALID_PARAM("0002", "요청한 값이 올바르지 않습니다."),


    // JWT 관련 ( 0100 ~ 0199 )
    VALIDATE_TOKEN_FAIL("0101", "토큰이 유효하지 않습니다."),
    TOKEN_SIGNATURE_FAIL("0102", "토큰의 서명이 유효하지 않습니다."),
    NOT_SUPPORTED_TOKEN("0103", "지원되지 않는 토큰입니다."),
    ILLEGAL_TOKEN("0104", "토큰이 잘못되었습니다."),
    ;

    private final String code;
    private final String message;
}
