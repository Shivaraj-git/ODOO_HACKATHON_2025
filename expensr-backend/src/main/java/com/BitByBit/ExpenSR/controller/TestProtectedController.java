package com.BitByBit.ExpenSR.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestProtectedController {
    @GetMapping("/api/protected/test")
    public String testProtectedEndpoint() {
        return "This is a protected endpoint. JWT required.";
    }
}
