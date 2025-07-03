package com.lgm.boilerplate.response;


import com.lgm.boilerplate.exception.CommonResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record ApiResponse<T>(
        boolean success,

        String code,

        String message,

        T data) {

    public static <T> ResponseEntity<ApiResponse<T>> success(CommonResponseCode responseCode) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, responseCode.getCode(), responseCode.getMessage(), null));
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(CommonResponseCode responseCode, T data) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, responseCode.getCode(), responseCode.getMessage(), data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> fail(CommonResponseCode responseCode) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, responseCode.getCode(), responseCode.getMessage(), null));
    }

    public static <T> ResponseEntity<ApiResponse<T>> fail(CommonResponseCode responseCode, T data) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, responseCode.getCode(), responseCode.getMessage(), data));
    }

    public static ResponseEntity<ApiResponse<String>> error(CommonResponseCode responseCode, RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, responseCode.getCode(), responseCode.getMessage(), exception.getMessage()));
    }

}
