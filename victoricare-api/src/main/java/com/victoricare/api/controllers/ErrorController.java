package com.victoricare.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/errors")
public class ErrorController {

    @GetMapping("/access-denied")
    public ResponseEntity<String> index() {
        log.error("access_denied", "error");
        return ResponseEntity.notFound().build();
        // "Access denied";
    }

}
