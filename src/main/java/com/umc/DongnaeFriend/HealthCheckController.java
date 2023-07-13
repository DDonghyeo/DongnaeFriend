package com.umc.DongnaeFriend;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping("/health")
    public String healthCheck() {
        return "health check ok!!!!!...";
    }
}