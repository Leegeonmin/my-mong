package com.lgm.my_mong.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthContoller {


    @GetMapping
    public ResponseEntity<?> health() {
        return ResponseEntity.ok().build();
    }
}
