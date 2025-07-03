package com.lgm.boilerplate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode implements CommonResponseCode {


    METHOD_ARG_NOT_VALID("0001", "유효성 오류가 발생했습니다."),
    COMMON_INVALID_PARAM("0002", "요청한 값이 올바르지 않습니다.")

    ;

    private final String code;
    private final String message;
}
