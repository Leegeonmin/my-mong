package com.lgm.my_mong.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode implements CommonResponseCode {

    SUCCESS("0200", "성공했습니다."),


    METHOD_ARG_NOT_VALID("0001", "유효성 오류가 발생했습니다."),
    COMMON_INVALID_PARAM("0002", "요청한 값이 올바르지 않습니다."),


    // JWT 관련 ( 0300 ~ 0399 )
    VALIDATE_TOKEN_FAIL("0301", "토큰이 유효하지 않습니다."),
    TOKEN_SIGNATURE_FAIL("0302", "토큰의 서명이 유효하지 않습니다."),
    NOT_SUPPORTED_TOKEN("0303", "지원되지 않는 토큰입니다."),
    ILLEGAL_TOKEN("0304", "토큰이 잘못되었습니다."),


    // auth 관련 (0400 ~ 0499)
    DUPLICATE_USERNAME("0400", "중복 아이디가 존재합니다"),
    DUPLICATE_NICKNAME("0401", "중복 닉네임이 존재합니다"),
    USER_NOT_FOUND("0402", "사용자가 존재하지 않습니다"),
    INVALID_PASSWORD("0403", "비밀번호가 유효하지 않습니다"),


    // mong 생성 관련 (0500 ~ 0599)
    OPENAI_ERROR("0500", "서버 요청 에러"),
    JSON_PARSING_ERROR("0501", "서버 Mong 생성 JSON 파싱 에러"),
    ALREADY_MONG_EXIST("0502", "이미 Mong이 존재합니다"),

    // rest client 관련 에러(0600 ~ 0699)
    OPENAI_400_ERROR("0600", "OPEN AI 요청 에러"),
    OPENAI_500_ERROR("0601", "OPEN AI 서버 에러")
    ;

    private final String code;
    private final String message;
}
