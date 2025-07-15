package com.lgm.my_mong.controller;

import com.lgm.my_mong.dto.MongDTO;
import com.lgm.my_mong.response.ApiResponse;
import com.lgm.my_mong.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/generate-monster")
    public ResponseEntity<ApiResponse<MongDTO>> generateMonster(@AuthenticationPrincipal String username) {
        MongDTO result = aiService.generateMonster(username);
        return ApiResponse.success(result);

    }

//    @PostMapping("/judge-battle")
//    public ResponseEntity<ApiResponse<String>> judgeBattle(
//            @RequestParam String challenger,
//            @RequestParam String opponent) {
//
//        String result = aiService.judgeBattle(challenger, opponent);
//        return ApiResponse.success(result);
//
//    }
}