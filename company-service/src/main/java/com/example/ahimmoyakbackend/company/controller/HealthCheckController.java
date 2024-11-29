package com.example.ahimmoyakbackend.company.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/company")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Health Check Passed!");
    }
}
