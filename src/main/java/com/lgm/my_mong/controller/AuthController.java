package com.lgm.my_mong.controller;

import com.lgm.my_mong.dto.AuthDTO;
import com.lgm.my_mong.response.ApiResponse;
import com.lgm.my_mong.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AuthDTO.TokenResponse>> signUp(
            @Valid @RequestBody AuthDTO.SignUpRequest request) {

        AuthDTO.TokenResponse response = userService.signUp(request);
        return ApiResponse.success(response);
    }

    /**
     * 로그인
     */
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<AuthDTO.TokenResponse>> signIn(
            @Valid @RequestBody AuthDTO.SignInRequest request) {


        AuthDTO.TokenResponse response = userService.signIn(request);
        return ApiResponse.success(response);
    }

    /**
     * 현재 사용자 정보 조회
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AuthDTO.UserInfo>> getCurrentUser(
            Authentication authentication) {

        String username = authentication.getName();

        AuthDTO.UserInfo response = userService.getCurrentUser(username);
        return ApiResponse.success(response);
    }

    /**
     * 토큰 유효성 검증 (헬스체크용)
     */
    @GetMapping("/validate")
    public ResponseEntity<ApiResponse<String>> validateToken(
            Authentication authentication) {

        String username = authentication.getName();
        return ApiResponse.success(username);
    }
}
