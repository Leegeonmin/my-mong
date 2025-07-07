package com.lgm.my_mong.controller;

import com.lgm.my_mong.response.ApiResponse;
import com.lgm.my_mong.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<String>> testConnection() {
        String result = aiService.testConnection();
        return ApiResponse.success(result);
    }

    @PostMapping("/generate-monster")
    public ResponseEntity<ApiResponse<String>> generateMonster() {
        String result = aiService.generateMonster();
        return ApiResponse.success(result);

    }

    @PostMapping("/judge-battle")
    public ResponseEntity<ApiResponse<String>> judgeBattle(
            @RequestParam String challenger,
            @RequestParam String opponent) {

        String result = aiService.judgeBattle(challenger, opponent);
        return ApiResponse.success(result);

    }
}