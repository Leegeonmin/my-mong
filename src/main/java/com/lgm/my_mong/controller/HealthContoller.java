package com.lgm.my_mong.controller;

import com.lgm.my_mong.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/health")
@RequiredArgsConstructor
public class HealthContoller {

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<?> health() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    public String validateTestToken(@RequestParam String token) {
        boolean isValid = jwtTokenProvider.validateToken(token);
        return "토큰 유효성: " + isValid;
    }

    @GetMapping("/test/token")
    public ResponseEntity<?> testToken() {
        String dev = jwtTokenProvider.createToken("dev");
        return ResponseEntity.ok().body(dev);

    }

    @GetMapping("/test/token/validate")
    public ResponseEntity<?> testToken2(@RequestParam String token) {
        boolean isTokenValidate = jwtTokenProvider.validateToken(token);
        return ResponseEntity.ok().body("token validate : " + isTokenValidate);
    }
}
