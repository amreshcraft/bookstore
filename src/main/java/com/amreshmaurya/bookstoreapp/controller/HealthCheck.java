package com.amreshmaurya.bookstoreapp.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheck {

    @GetMapping
    public ResponseEntity<HashMap<String,String>> healthCheck(){
        HashMap<String,String> map = new HashMap<>();
        map.put("status", "success");
        map.put("message", "It is Working!!");
        return ResponseEntity.ok(map);

    }
    
    
}
