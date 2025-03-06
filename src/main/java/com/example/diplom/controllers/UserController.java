package com.example.diplom.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal Jwt principal) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", principal.getSubject());
        userInfo.put("roles", principal.getClaimAsStringList("roles"));
        return ResponseEntity.ok(userInfo);
    }
}